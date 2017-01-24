package project.marky.oc.docu.internal.parsers;

import java.util.ArrayList;
import java.util.List;

import project.marky.oc.docu.internal.C4TypeDef;
import project.marky.oc.docu.internal.Function;
import project.marky.oc.docu.internal.Parameter;
import project.marky.oc.docu.internal.RegexMatcher;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.internal.interfaces.IParameter;
import static project.marky.oc.docu.internal.parsers.Regex.*;

/**
 * Parses a string so that the individual strings can be converted to functions.
 */
public class FunctionParser
{
	private final String _docu;
	private final String _declaration;


	FunctionParser(final String content)
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
			return _declaration.replaceAll(REGEX_ACCESS_MODIFIER + ".*", "$1");
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


	List<IParameter> getParameterDefinitions()
	{
		final List<IParameter> parameters = new ArrayList<IParameter>();

		for (final String line : getParameters())
		{
			final String type = line.replaceAll("\\s*(\\w+)\\s+.*", "$1");
			final String name = line.replaceAll("\\s*\\w+\\s+(.*)", "$1");

			final Parameter parameter = new Parameter(name, null, C4TypeDef.fromString(type));
			parameters.add(parameter);
		}

		return parameters;
	}


	String getDocu()
	{
		return _docu;
	}


	/**
	 * Parses a function and converts it to a {@link IFunction}.
	 * 
	 * @param content
	 *            the function string that was parsed.
	 * @return an {@link Function} object.
	 */
	public static IFunction parse(final String content)
	{
		// parsers
		final FunctionParser parser = new FunctionParser(content);
		final DocuParser parserDocu = new DocuParser(content);

		// the object
		final Function function = new Function();

		// basic docu stuff
		function.setIdentifier(parser.getFunctionName());
		function.setAuthor(parserDocu.getAuthor());
		function.setCategory(parserDocu.getCategory());
		function.setCredits(parserDocu.getCredits());
		function.setDescription(parserDocu.getDescription());
		function.setEngine(parserDocu.getEngine());
		function.setExample(parserDocu.getExample());
		function.setNote(parserDocu.getNote());
		function.setTitle(parser.getFunctionName());
		function.setVersion(parserDocu.getVersion());

		// function stuff
		function.setAccessModifier(parser.getAccessModifier());

		for (final IParameter parameter : parser.getParameterDefinitions())
		{
			((Parameter) parameter).setDocu(parserDocu.getParameters().get(parameter.getName()));

			function.getParameters().add(parameter);
		}

		final String[] typeAndDocu = parserDocu.getReturnValue();

		function.setReturnValue(new Parameter("return value", typeAndDocu[1], C4TypeDef.fromString(typeAndDocu[0])));

		return function;
	}
}
