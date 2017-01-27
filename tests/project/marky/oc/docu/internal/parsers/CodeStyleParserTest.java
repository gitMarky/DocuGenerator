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
		assertEquals("func blub(int, string)", CodeStyleParser.resolveTypes("func blub(int, string"));
		assertEquals("func blub(<b>int</b> string, parameter)", CodeStyleParser.resolveTypes("func blub(int string, parameter"));

	}
}
