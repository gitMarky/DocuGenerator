package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

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

		final String expected = new StringBuilder()
		.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
		.append(" * This is a simple docu header.").append(StringConstants.NEWLINE_STRING_WIN)
		.append(" */").toString();

		assertEquals(expected, match);
	}


	/**
	 * The class can find documented functions.
	 */
	@Test
	public void testDocumentedFunctions()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuFunctions.txt"));
		final PageParser page = new PageParser(content);

		final List<String> matches = page.getDocumentedFunctions();

		final String[] expected =
			{
				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a function comment for a public function.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("public func foo()").toString(),

				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a function comment for a protected function.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("protected func safe()").toString(),


				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a function comment for a private function.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("private func bar()").toString(),

				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This is a function comment without access modifier.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("func noModifier()").toString(),

				new StringBuilder()
				.append("/** This is a one line function comment. */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("func test()").toString(),

				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" This comment has no star.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("public func blub()").toString(),

				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This function has a parameter").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" *").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * @par number a number.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("public func withParameter(int number)").toString(),

				new StringBuilder()
				.append("/**").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * This function has several parameters").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" *").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * @par number a number.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * @par two a description.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" *").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" * @return proplist the number and description.").append(StringConstants.NEWLINE_STRING_WIN)
				.append(" */").append(StringConstants.NEWLINE_STRING_WIN)
				.append("public func withParameters(int number, string two)").toString()

			};

		//for (final String match : matches)
		//{
		//	System.out.println(match);
		//};

		for (int i = 0; i < matches.size(); ++i)
		{
			assertEquals(expected[i], matches.get(i));
		}
	}


	/**
	 * The class can find functions.
	 */
	@Test
	public void testFunctions()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuFunctions.txt"));
		final PageParser page = new PageParser(content);

		final List<String> matches = page.getFunctions();

		for (final String match : matches)
		{
			System.out.println(match);
		};
	}
}
