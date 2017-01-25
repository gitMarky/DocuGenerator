package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

import project.marky.oc.docu.internal.parsers.StyleParser;

public class StyleParserTest
{
	@Test
	public void testLinebreak()
	{
		assertEquals("text1<br>text2", StyleParser.resolve("text1@brtext2", null, null, null));
	}


	@Test
	public void testQuotes()
	{
		assertEquals("text1 \"text2\" text3 \"text4", StyleParser.resolve("text1 @qtest2@q text3 @qtext4", null, null, null));
	}


	@Test
	public void testBold()
	{
		assertEquals("text1 <b>bold</b> text2", StyleParser.resolve("text1 {@b bold} text2", null, null, null));
	}


	@Test
	public void testCursive()
	{
		assertEquals("text1 <i>italic</i> text2", StyleParser.resolve("text1 {@i italic} text2", null, null, null));
	}


	@Test
	public void testLink()
	{
		assertEquals("{@link link}", StyleParser.resolve("{@link link}", null, null, null));
		fail("Functionality not implemented yet");
	}


	@Test
	public void testCode()
	{
		assertEquals("text1 {@code code example} text2", StyleParser.resolve("text1 {@code code example} text2", null, null, null));
		fail("Functionality not implemented yet");
	}


	@Test
	public void testCodeShort()
	{
		assertEquals("text {@c code example} text2", StyleParser.resolve("text1 {@c code example} text2", null, null, null));
		fail("Functionality not implemented yet");

	}


	@Test
	public void testImage()
	{
		assertEquals("Not implemented yet", StyleParser.resolve("text1 {@img ...}", null, null, null));
	}


	@Test
	public void testSection()
	{

	}


	@Test
	public void testCodeBold()
	{
		assertEquals("{@c code here <b>and bold</b>}", StyleParser.resolve("{@c code here {@b and bold}}", null, null, null));
	}


	@Test
	public void testItalicBold()
	{
		assertEquals("<i>italic here <b>and bold</b></i>", StyleParser.resolve("{@i italic here {@b and bold}}", null, null, null));
	}


	@Test
	public void testCombinationOfTags()
	{
		assertEquals("<b>this contains <i>{@c a crazy <b>compilation</b> of}</i>multiple</b> tags", StyleParser.resolve("{@b this contains {@i {@c a crazy {@b compilation} of}}multiple} tags", null, null, null));
	}


	@Test
	public void testMultiLine()
	{
		assertEquals("{@c code\nwith multi\nline} blub", StyleParser.resolve("{@c code\nwith multi\nline} blub", null, null, null));
	}
}
