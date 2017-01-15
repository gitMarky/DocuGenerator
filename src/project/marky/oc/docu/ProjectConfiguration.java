package project.marky.oc.docu;

import java.io.File;


/**
 * Data structure that handles the project configuration.
 */
public class ProjectConfiguration
{
	private final File _workspace;
	private final File _outputFolder;
	private final File _stylesheet;
	private final String _title;


	/**
	 * Creates a new project configuration.
	 * 
	 * @param title the title of the project. Will be displayed in the HtmlHelp pages.
	 * @param docuSource this file is the source folder for a directory.
	 * @param docuOutput
	 * @param stylesheet
	 */
	public ProjectConfiguration(final String title, final File docuSource, final File docuOutput, final File stylesheet)
	{
		if (title == null)
		{
			throw new IllegalArgumentException("Title must not be null");
		}
		if (docuSource == null)
		{
			throw new IllegalArgumentException("Docu source must not be null");
		}
		else if (!docuSource.isDirectory())
		{
			throw new IllegalArgumentException("The docu source must be a directory: " + docuSource.getAbsolutePath());
		}
		if (docuOutput == null)
		{
			throw new IllegalArgumentException("Docu output must not be null");
		}
		else if (!docuOutput.isDirectory())
		{
			throw new IllegalArgumentException("The docu output must be a directory: " + docuOutput.getAbsolutePath());
		}
		if (stylesheet == null)
		{
			throw new IllegalArgumentException("Stylesheet must not be null");
		}
		else if (!stylesheet.isFile())
		{
			throw new IllegalArgumentException("Stylesheet must be a file: " + stylesheet.getAbsolutePath());
		}

		_title = title;
		_workspace = docuSource;
		_outputFolder = docuOutput;
		_stylesheet = stylesheet;
	}


	File getSource()
	{
		return _workspace;
	}


	File getOutput()
	{
		return _outputFolder;
	}


	File getStylesheet()
	{
		return _stylesheet;
	}


	String getTitle()
	{
		return _title;
	}
}
