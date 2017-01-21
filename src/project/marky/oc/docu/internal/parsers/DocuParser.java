package project.marky.oc.docu.internal.parsers;

import project.marky.oc.docu.internal.RegexMatcher;
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
				.replaceAll(" @", StringConstants.NEWLINE_STRING + "@");
	}


	public String getTitle()
	{
		return getTagContent("@title");
	}


	public String getAuthor()
	{
		return getTagContent("@author");
	}


	public String getCredits()
	{
		return getTagContent("@credits");
	}


	public String getVersion()
	{
		return getTagContent("@version");
	}


	public String getEngine()
	{
		return getTagContent("@engine");
	}


	public String getNote()
	{
		return getTagContent("@note");
	}


	public String getExample()
	{
		return getTagContent("@example");
	}


	public String getCategory()
	{
		return getTagContent("@category");
	}


	public String getDescription()
	{
		return _docu.replaceAll("(?m)^(.+)[.\\n]+", "$1");
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
}
