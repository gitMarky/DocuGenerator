package project.marky.oc.docu.internal.parsers;


/**
 * Parses code and puts certain keywords in a specific html format.
 */
public final class CodeStyleParser
{
	private enum CodeKeywords
	{
		// loop control
		code_for("for"),
		code_while("while"),
		code_break("break;"),
		code_continue("continue;"),
		// functions
		code_func("func"),
		code_public("public"),
		code_protected("protected"),
		code_global("global"),
		code_private("private"),
		// conditionals
		code_if("if"),
		code_else("else"),
		code_return("return"),
		code_true("true"),
		code_false("false"),
		// misc
		code_include("#include"),
		code_appendto("#appendto"),
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


	public static String resolve(final String content)
	{
		return content;
	}


	static String resolveSingleKeywords(final String content)
	{
		String resolved = content;

		for (final CodeKeywords keyword : CodeKeywords.values())
		{
			resolved = resolved.replaceAll(keyword.get(), "<b>" + keyword.get() + "</b>");
		}

		return resolveMultiBold(resolved);
	}

	static String resolveMultiBold(final String content)
	{
		return content.replaceAll("</b>(\\s*)<b>", "$1");
	}
}
