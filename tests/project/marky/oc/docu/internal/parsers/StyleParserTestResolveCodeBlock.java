package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import project.marky.oc.docu.internal.parsers.StyleParser;
import project.marky.oc.docu.util.LoadFile;

public class StyleParserTestResolveCodeBlock
{
	@Test
	public void testCodeBlock()
	{
		final File file = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\CodeBlock.txt");
		System.out.println(file.getAbsolutePath());
		final String content = LoadFile.getFileContent(file);
		final String resolved = StyleParser.resolveCodeBlocks(content);
		assertEquals("fail", resolved);
	}
}
