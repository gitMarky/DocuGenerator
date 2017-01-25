package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

import project.marky.oc.docu.internal.parsers.StyleParser;

public class StyleParserTestResolveKeywords
{
	@Test
	public void testLinebreak()
	{
		assertEquals("text1 <br>text2<br> text3 <br>text4", StyleParser.resolveSingleKeywords("text1 @brtext2@br text3 @brtext4"));
	}


	@Test
	public void testQuotes()
	{
		assertEquals("text1 \"text2\" text3 \"text4", StyleParser.resolveSingleKeywords("text1 @qtext2@q text3 @qtext4"));
	}
}
