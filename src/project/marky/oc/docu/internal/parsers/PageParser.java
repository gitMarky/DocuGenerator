package project.marky.oc.docu.internal.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.marky.oc.docu.internal.RegexMatcher;
import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.util.LoadFile;
import static project.marky.oc.docu.internal.parsers.Regex.*;

/**
 * Parses file content and returns a page, if possible.
 */
public class PageParser
{
	private final String _content;

	private static final Map<File, PageParser> _contents = new HashMap<File, PageParser>();

	public PageParser(final String content)
	{
		if (content == null)
		{
			throw new IllegalArgumentException("Content must not be null!");
		}

		_content = content;
	}


	public static IDocuItem getHeaderDocu(final File file)
	{
		final PageParser parser = getParser(file);

		return DocuParser.parse(parser.getHeader());
	}


	public static List<IDocuItem> getHeaderList(final File file)
	{
		final PageParser parser = getParser(file);

		final List<IDocuItem> headers = new ArrayList<IDocuItem>();

		for (final String header : parser.getHeaders())
		{
			if (!"".equals(header))
			{
				headers.add(DocuParser.parse(header));
			}
		}
		return headers;
	}


	public static List<IFunction> getFunctionList(final File file, final boolean includeUndocumentedFunctions)
	{
		final PageParser parser = getParser(file);

		final List<IFunction> functions = new ArrayList<IFunction>();

		final List<String> list;
		if (includeUndocumentedFunctions)
		{
			list = parser.getFunctionsWithDocuIfPossible();
		}
		else
		{
			list = parser.getDocumentedFunctions();
		}

		for (final String function : list)
		{
			functions.add(FunctionParser.parse(function));
		}

		return functions;
	}


	private static PageParser getParser(final File file)
	{
		final PageParser parser = _contents.get(file);
		if (parser != null)
		{
			return parser;
		}
		else
		{
			final PageParser newParser = new PageParser(LoadFile.getFileContent(file));
			_contents.put(file, newParser);
			return newParser;
		}
	}


	String getHeader()
	{
		try
		{
			return getHeaders().get(0);
		}
		catch (final IndexOutOfBoundsException e)
		{
			return "";
		}
	}


	List<String> getHeaders()
	{
		final String expression = REGEX_DOCU_ONLY;
		return RegexMatcher.getAllMatches(_content, expression);
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
