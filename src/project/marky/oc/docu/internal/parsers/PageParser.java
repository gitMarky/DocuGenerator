package project.marky.oc.docu.internal.parsers;

import java.util.List;

import project.marky.oc.docu.internal.RegexMatcher;

/**
 * Parses file content and returns a page, if possible.
 */
public class PageParser
{
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
		// search for /\*\*([^\*]|\*(?!/))*\*/
		final String expression = "/\\*\\*([^\\*]|\\*(?!/))*\\*/";  //"/\\\\*\\\\*.*";// "\\\\*/";
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches.iterator().next();
	}
}
