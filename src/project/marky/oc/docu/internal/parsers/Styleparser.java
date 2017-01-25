package project.marky.oc.docu.internal.parsers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.internal.RegexMatcher;
import project.marky.oc.docu.internal.StdNamespace;
import project.marky.oc.docu.util.RelFilePath;


/**
 * <p>
 * Style parser. Resolves style tags in the documentation.
 * </p><p>
 * For a list of supported enums, see the {@link StyleKeywords} enum.
 * </p>
 */
public class StyleParser
{
	public static String resolve(final String content, final DocuGenerator filemanager, final File root_folder, final File origin)
	{
		return resolveBlock(content);
		//return content;
		//final String text = parseBlock(content, filemanager, root_folder, origin);
		//return text;
	}


	private static String resolveBlock(final String content)
	{
		return resolveInnerBlocks(content);
	}


	enum StyleSingleKeywords
	{
		tag_linebreak("@br", "<br>"),
		char_string_escaped("@q", "\"");

		private final String _word;
		private final String _replace;

		StyleSingleKeywords(final String word, final String replace)
		{
			_word = word;
			_replace = replace;
		}

		private String get()
		{
			return _word;
		}

		private String replace()
		{
			return _replace;
		}
	}


	enum StyleBlockKeywords
	{
		tag_bold("@b"),
		tag_italic("@i"),
		tag_codestyle("@c"),
		tag_codeblock("@code"),
		tag_section("@section"),
		tag_link("@link"),
		tag_image("@img"),
		html_table("@table"),
		html_th("@th"),
		html_td("@td"),
		html_tr("@tr");


		private final String _word;

		StyleBlockKeywords(final String word)
		{
			_word = word;
		}

		String get()
		{
			return _word;
		}

		private static StyleBlockKeywords fromString(final String word)
		{
			for (final StyleBlockKeywords value : values())
			{
				if (value.get().equals(word))
				{
					return value;
				}
			}

			throw new IllegalArgumentException("Unknown keyword: " + word);
		}
	}

	private enum CodeKeywords
	{
		char_bracket_o("{"),
		char_bracket_c("}"),
		char_linebreak("\n"),
		code_break("break;"),
		sign_par_o("("),
		sign_par_c(")"),
		code_continue("continue;"),
		char_semicolon(";"),
		char_string("\""),
		line_comment("//"),
		block_comment_o("/bc"),
		block_comment_c("bc/"),
		code_func("func"),
		code_for("for"),
		code_return("return"),
		code_if("if"),
		code_else("else"),
		code_true("true"),
		code_false("false"),
		code_public("public"),
		code_protected("protected"),
		code_global("global"),
		code_private("private"),
		code_include("#include"),
		code_appendto("#appendto"),
		code_strict("#strict"),
		code_strict2("#strict2"),
		code_var("var");

		private final String _word;

		CodeKeywords(final String word)
		{
			_word = word;
		}

		private String get()
		{
			return _word;
		}
	}


	static String resolveInnerBlocks(final String content)
	{

		final String regex = "\\{@[" + Regex.REGEX_TEXT + Regex.REGEX_SPECIAL_CHARACTERS + "]+\\}";

		final List<String> matches = RegexMatcher.getAllMatches(content, regex);

		if (matches.isEmpty())
		{
			return content;
		}
		else
		{
			String resolved = content;

			for (final String match : matches)
			{
				resolved = resolved.replace(match, resolveInnerBlock(match));
			}

			// resolve the nextmost layer of nested blocks
			return resolveInnerBlocks(resolved);
		}
	}


	static String resolveInnerBlock(final String match)
	{
		final String regexBlock = "\\{(\\@\\w+)\\s+([." + Regex.REGEX_TEXT + Regex.REGEX_SPECIAL_CHARACTERS + "]*)\\}";
		final String keyword = match.replaceAll(regexBlock, "$1");
		final String text = match.replaceAll(regexBlock, "$2");

		final StyleBlockKeywords key = StyleBlockKeywords.fromString(keyword);

		switch (key)
		{
			case tag_bold:
			case tag_italic:
			case html_table:
			case html_th:
			case html_tr:
			case html_td:

				return buildHtmlBlock(key, text);
			default:
				throw new IllegalArgumentException("Not implemented yet: " + key.get());
		}
	}


	static String resolveSingleKeywords(final String content)
	{
		String resolved = content;

		for (final StyleSingleKeywords keyword : StyleSingleKeywords.values())
		{
			resolved = resolved.replaceAll(keyword.get(), keyword.replace());
		}

		return resolved;
	}


	private static String buildHtmlBlock(final StyleBlockKeywords key, final String text)
	{
		final String tag = key.get().replace("@", "");
		return "<" + tag + ">" + text + "</" + tag + ">";
	}


	private static String parseBlock(final String content, final DocuGenerator filemanager, final File root_folder, final File origin)
	{
		// ApplicationLogger.getLogger().info("parseBlock " + content);
		final String regex = "\\{(.+)\\}";

		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(content);

		if (!matcher.find())
		{
			return parseSingleTags(content);
		}
		else
		{

			final String subcontent = matcher.group(0);
			String newcontent = subcontent.substring(1, subcontent.length() - 1);

			if (newcontent.startsWith("@b "))
			{
				newcontent = parseBlock(newcontent.substring(3, newcontent.length()), filemanager, root_folder, origin);
				newcontent = "<b>" + newcontent + "</b>";
			}
			else if (newcontent.startsWith("@i "))
			{
				newcontent = parseBlock(newcontent.substring(3, newcontent.length()), filemanager, root_folder, origin);
				newcontent = "<i>" + newcontent + "</i>";
			}
			else if (newcontent.startsWith("@c "))
			{
				newcontent = parseBlock(newcontent.substring(3, newcontent.length()), filemanager, root_folder, origin);
				newcontent = "<code>" + newcontent + "</code>";
			}
			// else if (newcontent.startsWith("@table "))
			// {
			// newcontent = parseBlock(newcontent.substring(7,
			// newcontent.length()), filemanager, root_folder, origin);
			// newcontent = "<table>" + newcontent + "</table>";
			// }
			// else if (newcontent.startsWith("@th "))
			// {
			// newcontent = parseBlock(newcontent.substring(4,
			// newcontent.length()), filemanager, root_folder, origin);
			// newcontent = "<th>" + newcontent + "</th>";
			// }
			// else if (newcontent.startsWith("@td "))
			// {
			// newcontent = parseBlock(newcontent.substring(4,
			// newcontent.length()), filemanager, root_folder, origin);
			// newcontent = "<td>" + newcontent + "</td>";
			// }
			// else if (newcontent.startsWith("@tr "))
			// {
			// newcontent = parseBlock(newcontent.substring(4,
			// newcontent.length()), filemanager, root_folder, origin);
			// newcontent = "<tr>" + newcontent + "</tr>";
			// }
			else if (newcontent.startsWith("@img "))
			{
				// final File destination = new File(imageFolder,
				// _imageData._title);
				// final String path = RelFilePath.fromTo(doc.getLocation(),
				// destination);
				// stopDiv(doc, noDivs);
				// startDiv(doc, noDivs);
				// doc.builder().img(attributes().src(path));
				// stopDiv(doc, noDivs);
				// startDiv(doc, noDivs);
				newcontent = "[missing parser function: image]"; // TODO
			}
			else if (newcontent.startsWith("@section "))
			{
				newcontent = parseBlock(newcontent.substring(9, newcontent.length()), filemanager, root_folder, origin);
				newcontent = "<div class=\"text\">" + newcontent + "</div>";
			}
			else if (newcontent.startsWith("@link "))
			{
				newcontent = newcontent.substring(6, newcontent.length());

				int separator = newcontent.indexOf("#");

				if (separator < 0)
				{
					newcontent = "[invalid link: '" + newcontent + "']";
				}
				else
				{
					final String namespace = newcontent.substring(0, separator);
					String function = newcontent.substring(separator + 1, newcontent.length());

					String displayedText = namespace + "#" + function;

					if (function.contains(" "))
					{
						separator = function.indexOf(" ");
						displayedText = function.substring(separator + 1, function.length());
						function = function.substring(0, separator);
					}

					// newcontent = "[link: " + namespace + "::" + function +
					// "()]"; // TODO

					final StdNamespace space = filemanager.getNamespace(namespace);

					if (space != null)
					{
						final File destination = filemanager.getOutputFile(root_folder, namespace, function);

						final String path = RelFilePath.fromTo(origin, destination);
						newcontent = "<a href=\"" + path + "\">" + displayedText + "</a>";
					}
					else
					{
						newcontent = "[invalid link: '" + namespace + "#" + function + "']";
					}
				}
			}
			else
			{
				newcontent = parseBlock(newcontent, filemanager, root_folder, origin);
				newcontent = "{" + newcontent + "}";
			}

			// whoever actually writes exactly this in his script is probably
			// mad and does not deserve a proper docu :D
			final String crazysequence = "a_A_C7TxTxT9C_A_a";

			final String[] split = content.replace(subcontent, crazysequence).split(crazysequence);// content.split(subcontent);

			// if (split.length > 2)
			// {
			// throw new IllegalStateException("Not expected");
			// }
			// else
			// {
			// String pre = "";
			// String post = "";
			//
			// if (split.length >= 1)
			// {
			// pre = parseBlock(split[0], filemanager, root_folder, origin);
			// }
			// if (split.length == 2)
			// {
			// post = parseBlock(split[1], filemanager, root_folder, origin);
			// }
			//
			// return pre + newcontent + post;
			// }

			String value = "";
			for (int i = 0; i < split.length; i++)
			{
				final String parsed = parseBlock(split[i], filemanager, root_folder, origin);

				value += parsed;

				if (i == 0 || i < split.length - 2)
				{
					value += newcontent;
				}
			}

			return value;
		}
	}


	private static String parseSingleTags(final String content)
	{
		return content.replaceAll("\\@br", "<br>");
	}
}
