package project.marky.oc.docu.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class StdNamespaceManager
{
	final private HashMap<File, StdNamespace> _namespaces = new HashMap<File, StdNamespace>();

	final private StdNamespace _globalspace;
	final private StdNamespace _docuspace;


	public StdNamespaceManager()
	{
		_globalspace = new StdNamespace();
		_docuspace = StdNamespace.DocuNamespace();
	}


	public void addNamespace(final StdNamespace namespace)
	{
		_namespaces.put(namespace.getFile(), namespace);
	}


	public StdNamespace getNamespace(final File file)
	{
		final StdNamespace found = _namespaces.get(file);
		if (found != null)
		{
			return found;
		}
		else
		{
			return _globalspace;
		}
	}


	public StdNamespace getNamespaceDocu()
	{
		return _docuspace;
	}


	public StdNamespace getNamespaceGlobal()
	{
		return _globalspace;
	}


	public ArrayList<StdNamespace> getNamespaces()
	{
		final ArrayList<StdNamespace> spaces = new ArrayList<StdNamespace>();

		spaces.add(_docuspace);
		spaces.add(_globalspace);
		spaces.addAll(_namespaces.values());

		return spaces;
	}


	public StdNamespace getNamespace(final String identifier)
	{
		for (final StdNamespace space : getNamespaces())
		{
			if (space.getIdentifier().equals(identifier))
			{
				return space;
			}
		}

		return null;
	}
}
