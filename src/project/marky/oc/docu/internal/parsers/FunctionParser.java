package project.marky.oc.docu.internal.parsers;

import java.util.List;



import project.marky.oc.docu.internal.Function;
import project.marky.oc.docu.internal.RegexMatcher;
import project.marky.oc.docu.internal.interfaces.IFunction;
import static project.marky.oc.docu.internal.parsers.Regex.*;

/**
 * Parses a string so that the individual strings can be converted to functions.
 */
public class FunctionParser
{
	private final String _docu;
	private final String _declaration;

	public FunctionParser(final String content)
	{
		_declaration = RegexMatcher.getAllMatches(content, REGEX_FUNCTION).get(0);

		String docu = "";
		try
		{
			docu = RegexMatcher.getAllMatches(content, REGEX_DOCU).get(0);
		}
		catch (final IndexOutOfBoundsException e)
		{
			// do nothing
		}

		_docu = docu;
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
		}
	}


	String getFunctionName()
	{
		return _declaration.replaceAll(".*func\\s(\\w+)\\(.*\\)", "$1");
	}


	List<String> getParameters()
	{
		final String parameters = _declaration.replaceAll(".*func\\s\\w+\\((.*)\\)", "$1");

		return RegexMatcher.getAllMatches(parameters, "\\w[\\w\\s]+");
	}


	String getDocu()
	{
		return _docu;
	}


	public static IFunction parse(final String content)
	{
		final Function function = new Function();
		final FunctionParser parser = new FunctionParser(content);

		function.setAccessModifier(parser.getAccessModifier());
		function.setTitle(parser.getFunctionName());
		function.getParameters().addAll(parser.getParameters());

		return function;
	}
}
