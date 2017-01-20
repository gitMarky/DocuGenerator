package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FunctionParserTest
{
	private List<String> _functions;
	private FunctionParser[] _parsers;

	@Before
	public void setup()
	{
		_functions = new PageParser(Files.CONTENT_FUNCTIONS).getFunctionsWithDocuIfPossible();
		_parsers = new FunctionParser[_functions.size()];

		for (int i = 0; i < _functions.size(); ++i)
		{
			_parsers[i] = new FunctionParser(_functions.get(i));
		}
	}


	@Test
	public void testAccessModifier()
	{
		final String[] expected = {"public", "protected", "private", "", "", "public", "public", "public", "public", "", "", ""};


		for (int i = 0; i < Math.max(expected.length, _parsers.length); ++i)
		{
			final String accessModifier = _parsers[i].getAccessModifier();

			assertEquals(expected[i], accessModifier);
		}
	}


	@Test
	public void testFunctionName()
	{
		final String[] expected = {"foo", "safe", "bar", "noModifier", "test", "blub", "withParameter", "withParameters", "noDocu", "noDocu1", "noDocu2", "noDocu3"};

		for (int i = 0; i < Math.max(expected.length, _parsers.length); ++i)
		{
			final String accessModifier = _parsers[i].getFunctionName();

			assertEquals(expected[i], accessModifier);
		}
	}


	@Test
	public void testParameters()
	{
		final List<String> none = new ArrayList<String>();

		final List<String> number = new ArrayList<String>();
		number.add("int number");
		final List<String> numberString = new ArrayList<String>();
		numberString.add("int number");
		numberString.add("string two");
		final List<String> ok = new ArrayList<String>();
		ok.add("object ok");


		final Object[] expected = {none, none, none, none, none, none, number, numberString, ok, none, none, none};

		for (int i = 0; i < Math.max(expected.length, _parsers.length); ++i)
		{
			final List<String> parameters = _parsers[i].getParameters();

			assertEquals(expected[i], parameters);
		}

		// test the rest
		final List<String> rest = new ArrayList<String>();
		rest.add("int number");
		rest.add("string name");
		rest.add("object rock");
		rest.add("proplist properties");
		rest.add("array values");
		rest.add("any whatever");
		rest.add("unknown_type");

		assertEquals(rest, new FunctionParser("public func function(int number, string name, object rock, proplist properties, array values, any whatever, unknown_type)").getParameters());
	}
}
