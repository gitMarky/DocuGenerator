package project.marky.oc.docu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.marky.oc.docu.html.StdHtmlFile;
import static project.marky.oc.docu.StringConstants.*;

/**
 * HtmlHelp project file.
 *
 */
public class HtmlHelpProject extends StdHtmlFile
{
	private final File _contentsFile;
	private final File _indexFile;
	private final String _title;
	private final File _defaultFile;
	
	public HtmlHelpProject(File contents, File index, final String title, final File defaultFile)
	{
		super();

		_indexFile = index;
		_contentsFile = contents;
		_defaultFile = defaultFile;
		_title = title;

		writeHeader();
	}
	
	protected void writeHeader()
	{
		writeLine("[OPTIONS]");
		writeLine("Contents file=" + _contentsFile.getAbsolutePath());
		writeLine("Index file=" + _indexFile.getAbsolutePath());
		
		if (_title != null)			writeLine("Title=" + _title);
		if (_defaultFile != null)	writeLine("Default topic=" + _defaultFile.getAbsolutePath());
		
		writeLine(EMPTY_STRING);
		writeLine("[FILES]");
	}
}
