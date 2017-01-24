package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

import project.marky.oc.docu.internal.parsers.StyleParser;

public class StyleParserTest
{
	@Test
	public void testBold()
	{
		assertEquals("bla <b>this one should be bold</b> blu", StyleParser.parse("bla {@b this one should be bold} blu", null, null, null));
	}


	@Test
	public void testCursive()
	{
		assertEquals("<i>this one should be cursive</i>", StyleParser.parse("{@i this one should be cursive}", null, null, null));
	}


	@Test
	public void testLink()
	{
		assertEquals("{@link link}", StyleParser.parse("{@link link}", null, null, null));
	}


	@Test
	public void testCode()
	{
		assertEquals("{@code code example}", StyleParser.parse("{@code code example}", null, null, null));
	}


	@Test
	public void testCodeBold()
	{
		assertEquals("{@c code here <b>and bold</b>}", StyleParser.parse("{@c code here {@b and bold}}", null, null, null));
	}


	@Test
	public void testCombinationOfTags()
	{
		assertEquals("<b>this contains <i>{@c a crazy <b>compilation</b> of}</i>multiple</b> tags", StyleParser.parse("{@b this contains {@i {@c a crazy {@b compilation} of}}multiple} tags", null, null, null));
	}


	@Test
	public void testLists()
	{
		assertEquals("text with list stuff:<br>-blub<br>-bla<i>blu</i> bli", StyleParser.parse("text with list stuff:@br-blub@br-bla{@i blu} bli", null, null, null));
	}



	@Test
	public void testMultiLine()
	{
		assertEquals("{@c code\nwith multi\nline} blub", StyleParser.parse("{@c code\nwith multi\nline} blub", null, null, null));
	}
}
