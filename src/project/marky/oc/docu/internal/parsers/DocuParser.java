package project.marky.oc.docu.internal.parsers;

import project.marky.oc.docu.internal.DocuItem;
import project.marky.oc.docu.internal.RegexMatcher;
import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.util.StringConstants;

public class DocuParser
{
	final String _docu;

	DocuParser(final String content)
	{
		// ^\s*\**\s*(.+)\r*\n
		//_docu = content.replace("/**", "").replaceAll("\\s*\\*/", "").replaceAll("(?m)^\\s*\\**\\s*(.+)\\r*\\n","$1" + StringConstants.NEWLINE_STRING);
		_docu = content.replace("/**", "").replaceAll("\\s*\\*/", "")
				.replaceAll("(?m)^[\\s\\*]*(.+)\\r*\\n","$1" + StringConstants.NEWLINE_STRING)
				// put everything in one line, with spaces separating the stuff
				.replaceAll("\\n", " ")
				.replaceAll("\\s+", " ")
				// put tags in a new line
				.replaceAll(" @", StringConstants.NEWLINE_STRING + "@") + StringConstants.NEWLINE_STRING;
	}


	String getTitle()
	{
		return getTagContent("@title");
	}


	String getAuthor()
	{
		return getTagContent("@author");
	}


	String getCredits()
	{
		return getTagContent("@credits");
	}


	String getVersion()
	{
		return getTagContent("@version");
	}


	String getEngine()
	{
		return getTagContent("@engine");
	}


	String getNote()
	{
		return getTagContent("@note");
	}


	String getExample()
	{
		return getTagContent("@example");
	}


	String getCategory()
	{
		return getTagContent("@category");
	}


	String getDescription()
	{
		return _docu.replaceAll("(?m)^@(.+)", "").replaceAll("\\n", "");//.replaceAll("(.+)\\n[.\\n]+", "$1");
	}


	private String getTagContent(final String tag)
	{
		try
		{
			final String match = RegexMatcher.getAllMatches(_docu, "(?m)^" + tag + "(.+)\\n").get(0);
			return match.replaceAll(tag + "\\s*(.*)\\n", "$1");
		}
		catch (final IndexOutOfBoundsException e)
		{
			return null;
		}
	}


	/**
	 * Parses a documentation and converts it to a {@link IDocuItem}.
	 * 
	 * @param content the docu string that was parsed.
	 * @return a {@link DocuItem} object.
	 */
	public static IDocuItem parse(final String content)
	{
		final DocuItem docu = new DocuItem();
		final DocuParser parser = new DocuParser(content);

		docu.setAuthor(parser.getAuthor());
		docu.setCategory(parser.getCategory());
		docu.setCredits(parser.getCredits());
		docu.setDescription(parser.getDescription());
		docu.setEngine(parser.getEngine());
		docu.setExample(parser.getExample());
		docu.setNote(parser.getNote());
		docu.setTitle(parser.getTitle());
		docu.setVersion(parser.getVersion());

		return docu;
	}
}
