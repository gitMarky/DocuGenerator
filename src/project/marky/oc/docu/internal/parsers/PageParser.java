package project.marky.oc.docu.internal.parsers;

import java.util.List;

import project.marky.oc.docu.internal.RegexMatcher;

/**
 * Parses file content and returns a page, if possible.
 */
public class PageParser
{
	/**
	 * Regex that finds JavaDoc comments.
	 */
	// header: /\*\*([^\*]|\*(?!/))*\*/
	private static final String REGEX_DOCU = "/\\*\\*([^\\*]|\\*(?!/))*\\*/";

	// function "prefix": ^(public|protected|private)*.*func\s+
	// function prefix + declaration: ^(public|protected|private)*.*func\s+(\w+)\(\)
	// function prefix + declaration and parameters: ^(public|protected|private)*.*func\s+(\w+)\(([\w\s,]+)*\)
	/**
	 * Regex that finds function declarations, without the function body.
	 */
	//private static final String REGEX_FUNCTION = "^(public|protected|private)*.*func\\s+(\\w+)\\(([\\w\\s,]+)*\\)";
	private static final String REGEX_FUNCTION = "(public|protected|private)*\\s*func\\s+(\\w+)\\(([\\w\\s,]+)*\\)";

	/**
	 * Regex that finds line breaks.
	 */
	private static final String REGEX_ANY_LINEBREAK = "[\\r\\n]*";

	private final String _content;

	public PageParser(final String content)
	{
		if (content == null)
		{
			throw new IllegalArgumentException("Content must not be null!");
		}

		_content = content;
	}


	String getHeader()
	{
		final String expression = REGEX_DOCU;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches.iterator().next();
	}


	List<String> getDocumentedFunctions()
	{
		final String expression = REGEX_DOCU + REGEX_ANY_LINEBREAK + REGEX_FUNCTION;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches;
	}


	List<String> getFunctionsWithDocuIfPossible()
	{
		final String expression = "[REGEX_DOCU]*" + REGEX_ANY_LINEBREAK + REGEX_FUNCTION;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches;
	}


	List<String> getFunctions()
	{
		final String expression = REGEX_FUNCTION;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches;
	}
}
