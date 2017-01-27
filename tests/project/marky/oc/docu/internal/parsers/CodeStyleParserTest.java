package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodeStyleParserTest
{
	@Test
	public void testKeywords()
	{
		fail("Not implemented");
	}


	@Test
	public void testResolveMultiBold()
	{
		assertEquals("<b>this is bold\tstuff</b>", CodeStyleParser.resolveMultiBold("<b>this</b> <b>is bold</b>\t<b>stuff</b>"));
		assertEquals("<b>this is\r\nmultiline</b>", CodeStyleParser.resolveMultiBold("<b>this is</b>\r\n<b>multiline</b>"));
		assertEquals("<b>this</b> is <b>not</b>", CodeStyleParser.resolveMultiBold("<b>this</b> is <b>not</b>"));
	}


	@Test
	public void testResolveTypes()
	{
		assertEquals("public func doSomething(<b>int</b> blub, <b>string</b> bla)", CodeStyleParser.resolveTypes("public func doSomething(int blub, string bla)"));
		assertEquals("public func internal(<b>int</b> int, <b>string</b> string)", CodeStyleParser.resolveTypes("public func internal(int int, string string)"));
		assertEquals("func blub(into, stringle)", CodeStyleParser.resolveTypes("func blub(into, stringle)"));
		assertEquals("func blub(<b>int</b> string, parameter)", CodeStyleParser.resolveTypes("func blub(int string, parameter)"));
		assertEquals("func blub(int, string)", CodeStyleParser.resolveTypes("func blub(int, string)"));
		//		assertEquals("consists of <b>int</b>, <b>string</b>, or <b>object</b>", CodeStyleParser.resolveTypes("consists of int, string, or object"));
	}


	@Test
	public void testResolvedString()
	{
		final String content = "normal \"text\" stuff";
		final String expected = "normal <i class=\"string\">\"text\"</i> stuff";
		assertEquals(expected, CodeStyleParser.resolveStrings(content));
		assertEquals(expected, CodeStyleParser.resolveStrings(expected));
	}
}
