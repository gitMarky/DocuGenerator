package project.marky.oc.docu;

public enum C4FileTypes
{
	C4Group(".c4g"),
	C4Material(".c4m"),
	C4Definition(".c4d"),
	C4Folder(".c4f"),
	C4Player(".c4p");

	private final String _extension;
	
	C4FileTypes(final String extension)
	{
		_extension = extension;
	}
	
	public String getExtension()
	{
		return _extension;
	}
}
