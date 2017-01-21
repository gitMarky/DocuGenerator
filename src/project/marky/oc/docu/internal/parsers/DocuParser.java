package project.marky.oc.docu.internal.parsers;

import project.marky.oc.docu.util.StringConstants;

public class DocuParser
{
	final String _docu;

	DocuParser(final String content)
	{
		// ^\s*\**\s*(.+)\r*\n
		//_docu = content.replace("/**", "").replaceAll("\\s*\\*/", "").replaceAll("(?m)^\\s*\\**\\s*(.+)\\r*\\n","$1" + StringConstants.NEWLINE_STRING);
		_docu = content.replace("/**", "").replaceAll("\\s*\\*/", "").replaceAll("(?m)^[\\s\\*]*(.+)\\r*\\n","$1" + StringConstants.NEWLINE_STRING);
	}


	public String getTitle()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getAuthor()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getCredits()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getEngine()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getNote()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getExample()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getCategory()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
