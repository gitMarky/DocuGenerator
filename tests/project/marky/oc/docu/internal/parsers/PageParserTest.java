package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import project.marky.oc.docu.util.LoadFile;
import project.marky.oc.docu.util.StringConstants;

public class PageParserTest
{
	/**
	 * The class detects a header where there is one.
	 */
	@Test
	public void testCorrectHeader()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuHeader.txt"));
		final PageParser page = new PageParser(content);

		final String match = page.getHeader();

		final String expected = new StringBuilder().append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a simple docu header.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").toString();

		assertEquals(expected, match);
	}


	/**
	 * The class ignores function comments, etc. if there is no header.
	 */
	@Test
	public void testNoHeader()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuNoHeader.txt"));
		final PageParser page = new PageParser(content);

		final String match = page.getHeader();

		final String expected = new StringBuilder().append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a simple docu header.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").toString();

		assertEquals(expected, match);
	}
}
