package project.marky.oc.docu.internal;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;

import project.marky.library.xml.BasicXmlFile;
import project.marky.oc.docu.ApplicationLogger;
import project.marky.oc.docu.internal.interfaces.IProjectConfiguration;


/**
 * Data structure that handles the project configuration.
 */
public class ProjectConfiguration implements IProjectConfiguration
{
	private static final String XML_ROOT_ELEMENT = "docuProject";
	private static final String XML_ELEMENT_SOURCE = "source";
	private static final String XML_ELEMENT_OUTPUT = "output";
	private static final String XML_ELEMENT_STYLESHEET = "stylesheet";
	private static final String XML_ELEMENT_TITLE = "title";


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
			ApplicationLogger.getLogger().warning("# Stylesheet must be a file: " + stylesheet.getAbsolutePath());
		}

		_title = title;
		_workspace = docuSource;
		_outputFolder = docuOutput;
		_stylesheet = stylesheet;
	}


	public static IProjectConfiguration loadFromXml(final File xml)
	{
		final BasicXmlFile content = new BasicXmlFile(xml);

		if (!content.getRoot().getName().equals(XML_ROOT_ELEMENT))
		{
			throw new IllegalArgumentException("Invalid input file: " + xml.getAbsolutePath());
		}

		final String source = content.getRoot().getChildText(XML_ELEMENT_SOURCE);
		final String output = content.getRoot().getChildText(XML_ELEMENT_OUTPUT);
		final String sheet = content.getRoot().getChildText(XML_ELEMENT_STYLESHEET);
		final String title = content.getRoot().getChildText(XML_ELEMENT_TITLE);

		final File docuSource = new File(source);
		final File docuOutput = new File(output);
		final File stylesheet = new File(sheet);
		return new ProjectConfiguration(title, docuSource, docuOutput, stylesheet);
	}


	public static void saveToXmlFile(final IProjectConfiguration config, final File file)
	{
		// Setup content

		final Element title = new Element(XML_ELEMENT_TITLE);
		title.setText(config.getTitle());

		final Element source = new Element(XML_ELEMENT_SOURCE);
		source.setText(config.getSource().getAbsolutePath());

		final Element output = new Element(XML_ELEMENT_OUTPUT);
		output.setText(config.getOutput().getAbsolutePath());

		final Element style = new Element(XML_ELEMENT_STYLESHEET);
		style.setText(config.getStylesheet().getAbsolutePath());

		// Create document

		final Element rootElement = new Element(XML_ROOT_ELEMENT);

		rootElement.addContent(title);
		rootElement.addContent(source);
		rootElement.addContent(output);
		rootElement.addContent(style);

		final Document document = new Document(rootElement);

		final BasicXmlFile xml = new BasicXmlFile(document, file);
		xml.saveToFile();
	}


	@Override
	public File getSource()
	{
		return _workspace;
	}


	@Override
	public File getOutput()
	{
		return _outputFolder;
	}


	@Override
	public File getStylesheet()
	{
		return _stylesheet;
	}


	@Override
	public String getTitle()
	{
		return _title;
	}
}
