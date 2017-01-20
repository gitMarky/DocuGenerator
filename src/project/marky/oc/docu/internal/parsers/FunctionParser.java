package project.marky.oc.docu.internal.parsers;

import project.marky.oc.docu.internal.RegexMatcher;

import static project.marky.oc.docu.internal.parsers.Regex.*;

/**
 * Parses a string so that the individual strings can be converted to functions.
 */
public class FunctionParser
{
	private final String _content;
	private final String _declaration;

	public FunctionParser(final String content)
	{
		_content = content;
		_declaration = RegexMatcher.getAllMatches(content, REGEX_FUNCTION).get(0);
	}


	String getAccessModifier()
	{
		if (_declaration.startsWith("func"))
		{
			return "";
		}
		else
		{
			return _declaration.replaceAll("(public|protected|private).*", "$1");
			// this works, but it should be done with a match, imo.
			// return _declaration.split("\\s")[0];
		}
	}
}
