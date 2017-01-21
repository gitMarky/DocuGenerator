package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DocuParserTest
{
	private DocuParser _parser;

	private static final String APPENDIX = " also supports multiline may also contain {@code code stuff} or {@link link stuff}.";

	@Before
	public void setup()
	{
		_parser = new DocuParser(Files.CONTENT_ALL_TAGS);
	}

	@Test
	public void test()
	{
		System.out.println(_parser._docu);
		fail("Not yet implemented");
	}


	@Test
	public void testTitle()
	{
		assertEquals("The title" + APPENDIX, _parser.getTitle());
	}

}
