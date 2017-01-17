package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import project.marky.oc.docu.util.LoadFile;

public class PageParserTest
{
	@Test
	public void test()
	{
		final String content = LoadFile.getFileContent(new File("resources\\DocuHeader.txt"));
		final PageParser page = new PageParser(content);

		assertEquals(null, page.getHeader());
	}
}
