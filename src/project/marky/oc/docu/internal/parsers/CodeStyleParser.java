package project.marky.oc.docu.internal.parsers;


/**
 * Parses code and puts certain keywords in a specific html format.
 */
public final class CodeStyleParser
{
	private enum CodeKeywords
	{
		code_break("break;"),
		code_continue("continue;"),
		line_comment("//"),
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
}
