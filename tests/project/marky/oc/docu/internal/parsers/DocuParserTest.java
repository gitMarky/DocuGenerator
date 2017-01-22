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
	}


	@Test
	public void testTitle()
	{
		assertEquals("The title" + APPENDIX, _parser.getTitle());
	}


	@Test
	public void testAuthor()
	{
		assertEquals("The author" + APPENDIX, _parser.getAuthor());
	}


	@Test
	public void testCredits()
	{
		assertEquals("The credits" + APPENDIX, _parser.getCredits());
	}


	@Test
	public void testVersion()
	{
		assertEquals("The version" + APPENDIX, _parser.getVersion());
	}


	@Test
	public void testEngine()
	{
		assertEquals("The engine" + APPENDIX, _parser.getEngine());
	}


	@Test
	public void testNote()
	{
		assertEquals("The notes." + APPENDIX, _parser.getNote());
	}


	@Test
	public void testExample()
	{
		assertEquals("The example" + APPENDIX, _parser.getExample());
	}


	@Test
	public void testCategory()
	{
		assertEquals("The category." + APPENDIX, _parser.getCategory());
	}


	@Test
	public void testDescription()
	{
		assertEquals("<p> This is a multi-line docu description.</p> <p>It contains some text.</p> Even blank lines are recognized. Lines that start without the star are also recognized. Multiple spaces are skipped. May also contain {@code code stuff} or {@link link stuff}. Stuff that starts at the beginning of the line is not ignored.", _parser.getDescription());
	}


	@Test
	public void testParameters()
	{
		assertEquals(2, _parser.getParameters().size());
		assertEquals("this is a parameter" + APPENDIX, _parser.getParameters().get("input"));
		assertEquals("parameter" + APPENDIX, _parser.getParameters().get("second"));
	}
}
