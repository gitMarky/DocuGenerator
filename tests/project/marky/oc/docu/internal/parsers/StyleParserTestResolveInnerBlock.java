package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

import project.marky.oc.docu.internal.parsers.StyleParser;
import project.marky.oc.docu.internal.parsers.StyleParser.StyleBlockKeywords;

/**
 * Unit tests for {@link StyleParser#resolveInnerBlocks(String)}
 */
public class StyleParserTestResolveInnerBlock
{
	private static final String SAMPLE_TEXT = "this is a sample text";


	@Test
	public void testResolveBlockBold()
	{
		testBlock(StyleBlockKeywords.tag_bold);
	}


	@Test
	public void testResolveBlockItalic()
	{
		testBlock(StyleBlockKeywords.tag_italic);
	}


	@Test
	public void testResolveBlockCodestyle()
	{
		assertEquals("<code>" + SAMPLE_TEXT + "</code>", StyleParser.resolveInnerBlock(buildBlock(StyleBlockKeywords.tag_codestyle)));
	}


	@Test
	public void testResolveBlockCodeblock()
	{
		fail("Not implemented yet");
	}


	@Test
	public void testResolveBlockSection()
	{
		assertEquals("<div class=\"text\">" + SAMPLE_TEXT + "</div>", StyleParser.resolveInnerBlock(buildBlock(StyleBlockKeywords.tag_section)));
	}


	@Test
	public void testResolveBlockLink()
	{
		fail("Not implemented yet");
	}


	@Test
	public void testResolveBlockImage()
	{
		fail("Not implemented yet");
	}


	@Test
	public void testResolveBlockTable()
	{
		testBlock(StyleBlockKeywords.html_table);
	}


	@Test
	public void testResolveBlockTh()
	{
		testBlock(StyleBlockKeywords.html_th);
	}


	@Test
	public void testResolveBlockTd()
	{
		testBlock(StyleBlockKeywords.html_td);
	}


	@Test
	public void testResolveBlockTr()
	{
		testBlock(StyleBlockKeywords.html_tr);
	}


	private String buildBlock(final StyleBlockKeywords keyword)
	{
		return "{" + keyword.get() + " " + SAMPLE_TEXT + "}";
	}


	private String buildExpectedBlock(final StyleBlockKeywords keyword)
	{
		final String htmlKeyword = keyword.get().replace("@", "");
		return "<" + htmlKeyword + ">" + SAMPLE_TEXT + "</" + htmlKeyword + ">";
	}


	private void testBlock(final StyleBlockKeywords keyword)
	{
		assertEquals(buildExpectedBlock(keyword), StyleParser.resolveInnerBlock(buildBlock(keyword)));
	}

}
