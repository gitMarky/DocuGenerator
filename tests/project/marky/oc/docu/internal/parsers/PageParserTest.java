package project.marky.oc.docu.internal.parsers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import project.marky.oc.docu.internal.C4TypeDef;
import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.util.StringConstants;

public class PageParserTest
{
	/**
	 * The class detects a header where there is one.
	 */
	@Test
	public void testCorrectHeader()
	{
		final PageParser page = new PageParser(Files.CONTENT_WITH_HEADER);

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
		final PageParser page = new PageParser(Files.CONTENT_NO_HEADER);

		final String match = page.getHeader();

		final String expected = build(
				"/**",
				" This is the actual hidden header.",
				" */");

		assertEquals(expected, match);
	}


	/**
	 * The class can find documented functions.
	 */
	@Test
	public void testDocumentedFunctions()
	{
		final PageParser page = new PageParser(Files.CONTENT_FUNCTIONS);

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
		final PageParser page = new PageParser(Files.CONTENT_FUNCTIONS);

		final List<String> matches = page.getFunctions();

		//		for (final String match : matches)
		//		{
		//			System.out.println(match);
		//		};

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
				"public func noDocu(object ok)",
				"func noDocu1()",
				"func noDocu2()",
				"func noDocu3()"
			};

		for (int i = 0; i < matches.size(); ++i)
		{
			assertEquals(expected[i], matches.get(i));
		}
	}

	/**
	 * The class can find undocumented functions.
	 */
	@Test
	public void testUndocumentedFunctions()
	{
		final PageParser page = new PageParser(Files.CONTENT_FUNCTIONS);

		final List<String> matches = page.getUndocumentedFunctions();

		for (final String match : matches)
		{
			System.out.println(match);
		};

		final String[] expected = {
				"public func noDocu(object ok)",
				"func noDocu1()",
				"func noDocu2()",
				"func noDocu3()"
		};

		for (int i = 0; i < expected.length; ++i)
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
		final PageParser page = new PageParser(Files.CONTENT_FUNCTIONS);

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
																				"public func withParameters(int number, string two)"),

																				"public func noDocu(object ok)",
																				"func noDocu1()",
																				"func noDocu2()",
																				"func noDocu3()"
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


	@Test
	public void testHeaderParser()
	{
		final List<IDocuItem> headers = PageParser.getHeaderList(Files.FILE_WITH_HEADER);

		assertEquals(1, headers.size());
		assertEquals("This is a simple docu header.", headers.get(0).getDescription());
	}


	@Test
	public void testFunctionParser()
	{
		final List<IFunction> functions = PageParser.getFunctionList(Files.FILE_WITH_HEADER, false);

		assertEquals(1, functions.size());
		assertEquals("This is a function comment.", functions.get(0).getDescription());
		assertEquals("foo", functions.get(0).getTitle());
		assertEquals("public", functions.get(0).getAccessModifier());
		assertEquals(C4TypeDef.C4V_Any, functions.get(0).getReturnValue().getType());
	}
}
