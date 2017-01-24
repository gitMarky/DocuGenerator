package project.marky.oc.docu.internal;

import project.marky.oc.docu.internal.interfaces.IDocuItem;


/**
 * Represents a documentation item.
 */
public class DocuItem implements IDocuItem
{
	private String _description = null;
	private String _category = null;
	private String _example = null;
	private String _note = null;
	private String _engine = null;
	private String _version = null;
	private String _credits = null;
	private String _author = null;
	private String _title = null;
	private String _identifier;

	////////////////////////////////////////////////////////////////
	//
	// from interface

	@Override
	public String getIdentifier()
	{
		return _identifier;
	}

	@Override
	public String getTitle()
	{
		return _title;
	}

	@Override
	public String getAuthor()
	{
		return _author;
	}

	@Override
	public String getCredits()
	{
		return _credits;
	}

	@Override
	public String getVersion()
	{
		return _version;
	}

	@Override
	public String getEngine()
	{
		return _engine;
	}

	@Override
	public String getNote()
	{
		return _note;
	}

	@Override
	public String getExample()
	{
		return _example;
	}

	@Override
	public String getCategory()
	{
		return _category;
	}

	@Override
	public String getDescription()
	{
		return _description;
	}

	/////////////////////////////////////////////////////////////////
	//
	// setters

	public void setIdentifier(final String identifier)
	{
		_identifier = identifier;
	}


	public void setTitle(final String title)
	{
		_title = title;
	}


	public void setAuthor(final String author)
	{
		_author = author;
	}


	public void setCredits(final String credits)
	{
		_credits = credits;
	}


	public void setVersion(final String version)
	{
		_version = version;
	}


	public void setEngine(final String engine)
	{
		_engine = engine;
	}


	public void setNote(final String note)
	{
		_note = note;
	}


	public void setExample(final String example)
	{
		_example = example;
	}


	public void setCategory(final String category)
	{
		_category = category;
	}


	public void setDescription(final String description)
	{
		_description = description;
	}
}
