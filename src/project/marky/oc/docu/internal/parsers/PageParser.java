package project.marky.oc.docu.internal.parsers;

import java.util.ArrayList;
import java.util.List;

import project.marky.oc.docu.internal.RegexMatcher;
import static project.marky.oc.docu.internal.parsers.Regex.*;

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
		final String expression = REGEX_DOCU;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches.iterator().next();
	}


	List<String> getDocumentedFunctions()
	{
		final String expression = REGEX_DOCUMENTED_FUNCTIONS;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches;
	}


	List<String> getDocumentedFunctionsDeclarations()
	{
		final String expression = REGEX_DOCUMENTED_FUNCTIONS;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);

		final List<String> results = new ArrayList<String>();
		for (final String match : matches)
		{
			results.addAll(RegexMatcher.getAllMatches(match, REGEX_FUNCTION));
		}
		return results;
	}


	List<String> getFunctionsWithDocuIfPossible()
	{
		final List<String> results = new ArrayList<String>();

		results.addAll(getDocumentedFunctions());
		results.addAll(getUndocumentedFunctions());
		return results;
	}


	List<String> getFunctions()
	{
		final String expression = REGEX_FUNCTION;
		final List<String> matches = RegexMatcher.getAllMatches(_content, expression);
		return matches;
	}


	List<String> getUndocumentedFunctions()
	{
		final List<String> functions = getFunctions();
		final List<String> documented = getDocumentedFunctionsDeclarations();

		final List<String> results = new ArrayList<String>();
		for (final String function : functions)
		{
			if (!documented.contains(function))
			{
				results.add(function);
			}
		}
		return results;
	}
}
