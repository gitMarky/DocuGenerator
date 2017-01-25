package project.marky.oc.docu.internal;

import java.io.File;
import java.util.ArrayList;

public class StdNamespace
{
	private final File _parentfile;
	private final String _namespace;

	public static final String NAMESPACE_GLOBAL = "global";
	public static final String NAMESPACE_DOCU = "docu";

	private final ArrayList<DocuPage> _pages = new ArrayList<DocuPage>();


	StdNamespace()
	{
		_namespace = NAMESPACE_GLOBAL;
		_parentfile = null;
	}


	public StdNamespace(final String definition, final String name, final File path) // TODO: Remove the name-parameter since it is unused
	{
		_namespace = definition;
		_parentfile = path;
	}


	static StdNamespace DocuNamespace()
	{
		return new StdNamespace(NAMESPACE_DOCU, "Documentation", null);
	}


	public File getFile()
	{
		return _parentfile;
	}


	public void add(final DocuPage page)
	{
		_pages.add(page);
	}


	public String getIdentifier()
	{
		return _namespace;
	}


	public ArrayList<DocuPage> getPages()
	{
		return _pages;
	}
}
