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


	@Test public void testResolveMultiBold()
	{
		assertEquals("<b>this is bold\tstuff</b>", CodeStyleParser.resolveMultiBold("<b>this</b> <b>is bold</b>\t<b>stuff</b>"));
		assertEquals("<b>this is\r\nmultiline</b>", CodeStyleParser.resolveMultiBold("<b>this is</b>\r\n<b>multiline</b>"));
		assertEquals("<b>this</b> is <b>not</b>", CodeStyleParser.resolveMultiBold("<b>this</b> is <b>not</b>"));
	}
}
