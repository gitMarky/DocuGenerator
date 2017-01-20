package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

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
}
