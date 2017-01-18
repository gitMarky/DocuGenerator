package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import project.marky.oc.docu.util.LoadFile;
import project.marky.oc.docu.util.StringConstants;

public class PageParserTest
{
	@Test
	public void test()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuHeader.txt"));
		final PageParser page = new PageParser(content);

		final String match = page.getHeader();

		final String expected = new StringBuilder().append("/**").append(StringConstants.NEWLINE_STRING)
				.append(" * This is a simple docu header.").append(StringConstants.NEWLINE_STRING)
				.append(" */").toString();

		assertEquals(expected, match);
	}
}
