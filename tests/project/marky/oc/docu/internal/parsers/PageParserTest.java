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

		final String expected = build("/**",
				" * This is a simple docu header.",
				" */");

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

		final String expected = build(
				"/**",
				" * This is a simple docu header.",
				" */");

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
				build(
						"/**",
						" * This is a function comment for a public function.",
						" */",
						"public func foo()"),

						build(
								"/**",
								" * This is a function comment for a protected function.",
								" */",
								"protected func safe()"),


								build(
										"/**",
										" * This is a function comment for a private function.",
										" */",
										"private func bar()"),

										build(
												"/**",
												" * This is a function comment without access modifier.",
												" */",
												"func noModifier()"),

												build(
														"/** This is a one line function comment. */",
														"func test()"),

														build(
																"/**",
																" This comment has no star.",
																" */",
																"public func blub()"),

																build(
																		"/**",
																		" * This function has a parameter",
																		" *",
																		" * @par number a number.",
																		" */",
																		"public func withParameter(int number)"),

																		build(
																				"/**",
																				" * This function has several parameters",
																				" *",
																				" * @par number a number.",
																				" * @par two a description.",
																				" *",
																				" * @return proplist the number and description.",
																				" */",
																				"public func withParameters(int number, string two)")

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

		final String[] expected =
			{
				"public func foo()",
				"protected func safe()",
				"private func bar()",
				"func noModifier()",
				"func test()",
				"public func blub()",
				"public func withParameter(int number)",
				"public func withParameters(int number, string two)",
				"public func noDocu(object ok)"
			};

		for (int i = 0; i < matches.size(); ++i)
		{
			assertEquals(expected[i], matches.get(i));
		}
	}

	/**
	 * The class can find functions and add docu if possible.
	 */
	@Test
	public void testFunctionsWithOrWithoutDocu()
	{
		final String content = LoadFile.getFileContent(new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuFunctions.txt"));
		final PageParser page = new PageParser(content);

		final List<String> matches = page.getFunctionsWithDocuIfPossible();

		final String[] expected =
			{
				build(
						"/**",
						" * This is a function comment for a public function.",
						" */",
						"public func foo()"),

						build(
								"/**",
								" * This is a function comment for a protected function.",
								" */",
								"protected func safe()"),


								build(
										"/**",
										" * This is a function comment for a private function.",
										" */",
										"private func bar()"),

										build(
												"/**",
												" * This is a function comment without access modifier.",
												" */",
												"func noModifier()"),

												build(
														"/** This is a one line function comment. */",
														"func test()"),

														build(
																"/**",
																" This comment has no star.",
																" */",
																"public func blub()"),

																build(
																		"/**",
																		" * This function has a parameter",
																		" *",
																		" * @par number a number.",
																		" */",
																		"public func withParameter(int number)"),

																		build(
																				"/**",
																				" * This function has several parameters",
																				" *",
																				" * @par number a number.",
																				" * @par two a description.",
																				" *",
																				" * @return proplist the number and description.",
																				" */",
																				"public func withParameters(int number, string two)")

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


	private String build(final String... parts)
	{
		final StringBuilder builder = new StringBuilder();

		final int last = parts.length - 1;

		for (int i = 0; i < last; ++i)
		{
			builder.append(parts[i]).append(StringConstants.NEWLINE_STRING_WIN);
		}

		return builder.append(parts[last]).toString();
	}
}
