package project.marky.oc.docu.html;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.internal.StdNamespace;
import project.marky.oc.docu.util.RelFilePath;

public class Styleparser
{
	public static String parse(final String content, final DocuGenerator filemanager, final File root_folder, final File origin)
	{
		return content;
		//TODO: was: final String text = parseBlock(content, filemanager, root_folder, origin);
		//return text;
	}


	// // single tags
	// char_string_escaped("@q"),

	// // block tags
	// tag_codeblock("{@code"),

	// // code tags
	// line_comment("//"),
	// block_comment_o("/bc"),
	// block_comment_c("bc/"),
	// char_string("\""),
	//
	// code_func("func"),
	// code_for("for"),
	// code_return("return"),
	// code_if("if"),
	// code_else("else"),
	// code_true("true"),
	// code_false("false"),
	// code_public("public"),
	// code_protected("protected"),
	// code_global("global"),
	// code_private("private"),
	// code_include("#include"),
	// code_appendto("#appendto"),
	// code_strict("#strict"),
	// code_strict2("#strict2"),
	// code_var("var");
	// code_break("break;"),
	// code_continue("continue;"),

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
