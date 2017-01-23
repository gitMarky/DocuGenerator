package project.marky.oc.docu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.marky.oc.docu.external.htmlHelp.HtmlHelpContents;
import project.marky.oc.docu.external.htmlHelp.HtmlHelpIndex;
import project.marky.oc.docu.external.htmlHelp.HtmlHelpProject;
import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.internal.parsers.PageParser;
import project.marky.oc.docu.logic.DocuPage;
import project.marky.oc.docu.logic.StdNamespace;
import project.marky.oc.docu.logic.StdNamespaceManager;
import project.marky.oc.docu.util.LoadFile;
import project.marky.oc.docu.util.RelFilePath;

/**
 * The class that generates the html files and the project files for html help.
 */
public class DocuGenerator
{
	private final StdNamespaceManager _namespaces;


	public DocuGenerator()
	{
		_namespaces = new StdNamespaceManager();
	}


	/**
	 * Creates the documentation from a given configuration.
	 * 
	 * @param configuration the configuration.
	 */
	public void run(final IProjectConfiguration configuration)
	{
		run(configuration.getSource(), configuration.getOutput(), configuration.getStylesheet(), configuration.getTitle());
	}


	/**
	 * Creates the documentation files.
	 * 
	 * @param inputFolderProject
	 *            this is the folder that contains the script files.
	 * @param outputFolderProject
	 *            the generated filed will be created in this folder.
	 * @param cssStyleSheet
	 *            this is a css style sheet that defines how the documentation
	 *            will look like.
	 * @param title
	 *            this is the title of the documentation project in html help.
	 */
	private void run(final File inputFolderProject, final File outputFolderProject, final File cssStyleSheet, final String title)
	{
		final List<File> defCoreFiles = new ArrayList<File>();
		final List<File> scriptFiles = new ArrayList<File>();
		final List<File> docuFiles = new ArrayList<File>();

		// Read all relevant files in the input folder
		parseFolder(inputFolderProject, defCoreFiles, docuFiles, scriptFiles);

		// Parse the individual files for docu
		parseDefcoreFiles(defCoreFiles);
		parseScriptFiles(scriptFiles, docuFiles);

		// Create the HtmlHelpProject
		createHtmlHelpProject(outputFolderProject, cssStyleSheet, title);
	}


	private void createHtmlHelpProject(final File outputFolderProject, final File cssStyleSheet, final String title)
	{
		createHtmlFiles(outputFolderProject, cssStyleSheet);

		final File defaultFile = getOutputFile(outputFolderProject, _namespaces.getNamespaceDocu().getIdentifier(), "index");

		final File index = createIndexFile(outputFolderProject);
		final File contents = createContentsFile(outputFolderProject, defaultFile, title);
		createProjectFile(outputFolderProject, contents, index, title, defaultFile);
	}


	private void parseDefcoreFiles(final List<File> defCoreFiles)
	{
		ApplicationLogger.getLogger().info("DefCore Files:");

		for (final File file : defCoreFiles)
		{
			ApplicationLogger.getLogger().info(" * " + file.getAbsolutePath());
			parseDefcore(file);
		}
	}


	/**
	 * Parses a DefCore file. This is necessary to get the namespaces for docu links.
	 * 
	 * @param file
	 *            the file.
	 */
	private void parseDefcore(final File file)
	{
		final String content = LoadFile.getFileContent(file);

		final Pattern patternID = Pattern.compile("id=(.+)");
		final Matcher matcherID = patternID.matcher(content);

		if (matcherID.find())
		{
			final String definition = matcherID.group(0).replace("id=", "");

			final Pattern patternName = Pattern.compile("Name=(.+)");
			final Matcher matcherName = patternName.matcher(content);

			String name = "";

			if (matcherName.find())
			{
				name = matcherName.group(0).replace("Name=", "");
			}
			else
			{
				name = file.getParentFile().getName();

				// remove file endings
				name = name.substring(0, name.lastIndexOf("."));
			}

			ApplicationLogger.getLogger().info(" * > adding namespace '" + definition + "'");

			final StdNamespace namespace = new StdNamespace(definition, name, file.getParentFile());
			_namespaces.addNamespace(namespace);
		}
		else
		{
			ApplicationLogger.getLogger().info(" * > no ID found");
		}
	}


	private void parseScriptFiles(final List<File> scriptFiles, final List<File> docuFiles)
	{

		ApplicationLogger.getLogger().info("Docu Files:");

		for (final File file : docuFiles)
		{
			ApplicationLogger.getLogger().info(" * " + file.getAbsolutePath());
			parseScriptFile(file, true);
		}

		ApplicationLogger.getLogger().info("Script Files:");

		for (final File file : scriptFiles)
		{
			ApplicationLogger.getLogger().info(" * " + file.getAbsolutePath());
			parseScriptFile(file, false);
		}
	}


	private void parseScriptFile(final File file, final boolean addDocuToDocuNamespace)
	{
		//final StdNamespace space = _namespaces.getNamespace(parsedScript.getOrigin());

		//if (space == null)
		//{
		//	ApplicationLogger.getLogger().warning(" * > cannot add to namespace with origin '" + parsedScript.getOrigin() + "'");
		//}

		final StdNamespace space =null;

		for (final IDocuItem docu : PageParser.getHeaderList(file))
		{
			final DocuPage page = new DocuPage(docu);

			final String identifier = "temp"; // TODO

			if (space == null || addDocuToDocuNamespace)
			{
				addPageToNamespace(page, _namespaces.getNamespaceDocu(), identifier);
			}
			else
			{
				addPageToNamespace(page, space, identifier);
			}
		}

		for (final IFunction function : PageParser.getFunctionList(file, false))
		{
			final DocuPage page = new DocuPage(function);

			if (function.getAccessModifier() != null && function.getAccessModifier().equals(StdNamespace.NAMESPACE_GLOBAL))
			{
				addPageToNamespace(page, _namespaces.getNamespaceGlobal(), function.getTitle());
			}
			else
			{
				if (space != null)
				{
					addPageToNamespace(page, space, function.getTitle());
				}
			}
		}
	}


	private void addPageToNamespace(final DocuPage page, final StdNamespace space, final String identifier)
	{
		space.add(page);
		ApplicationLogger.getLogger().info(" * > added " + space.getIdentifier() + "#" + identifier);
	}


	private void parseFolder(final File sourceFolder, final List<File> defCoreFiles, final List<File> docuFiles, final List<File> scriptFiles)
	{
		if (sourceFolder.isFile())
		{
			// identify script files: either the name is script.c, or it is in a system.c4g/system.ocg file
			if (sourceFolder.getName().equalsIgnoreCase("script.c") || sourceFolder.getParentFile().getName().toLowerCase().contains("system."))
			{
				scriptFiles.add(sourceFolder);
			}
			// identify the documentation files: anything else that is named *.c
			else if (sourceFolder.getName().endsWith(".c"))
			{
				docuFiles.add(sourceFolder);
			}
			// identify defcore files. This should not be necessary anymore.
			else if (sourceFolder.getName().toLowerCase().equals("defcore.txt"))
			{
				defCoreFiles.add(sourceFolder);
			}
		}
		else if (sourceFolder.isDirectory())
		{
			// skip scenarios. one can argue that this is not necessary, but we do it at the moment.
			if (sourceFolder.getName().endsWith(".ocs") || sourceFolder.getName().endsWith(".c4s")) return;

			final File[] subFiles = sourceFolder.listFiles();

			for (final File file : subFiles)
			{
				parseFolder(file, defCoreFiles, docuFiles, scriptFiles);
			}
		}
	}


	private void createHtmlFiles(final File outputFolderProject, final File cssStyleSheet)
	{
		for (final StdNamespace namespace : _namespaces.getNamespaces())
		{
			ApplicationLogger.getLogger().info("## " + namespace.getIdentifier());

			for (final DocuPage page : namespace.getPages())
			{
				ApplicationLogger.getLogger().info("##### " + page.getIdentifier());

				final File outputFolderFile = getOutputFile(outputFolderProject, namespace.getIdentifier(), page.getIdentifier());

				final String css_location = RelFilePath.fromTo(outputFolderFile, cssStyleSheet);

				page.convertToHtml(this, outputFolderProject, outputFolderFile, css_location);
				page.saveHtmlFile(outputFolderFile);
			}
		}
	}


	private void createProjectFile(final File outputFolderProject, final File contents, final File index, final String title, final File defaultFile)
	{
		final HtmlHelpProject file = new HtmlHelpProject(contents, index, title, defaultFile);
		file.saveToFile(new File(outputFolderProject, "project.hhp"));
	}


	private File createIndexFile(final File outputFolderProject)
	{
		final HashMap<String, File> map = new HashMap<String, File>();

		for (final StdNamespace namespace : _namespaces.getNamespaces())
		{
			for (final DocuPage page : namespace.getPages())
			{
				final File outputFolderFile = getOutputFile(outputFolderProject, namespace.getIdentifier(), page.getIdentifier());
				final String identifier = page.getIdentifier();

				String name = identifier;

				for (int i = 2; map.keySet().contains(name); i++)
				{
					name = identifier + " (#" + i + ")";
				}

				map.put(name, outputFolderFile);
			}
		}

		final HtmlHelpIndex index = new HtmlHelpIndex(map);
		index.createContent(outputFolderProject);

		final File output = new File(outputFolderProject, "index.hhk");
		index.saveToFile(output);
		return output;
	}


	private File createContentsFile(final File outputFolderProject, final File defaultFile, final String title)
	{
		final HtmlHelpContents index = new HtmlHelpContents(this);
		index.createContent(outputFolderProject, defaultFile, title);

		final File output = new File(outputFolderProject, "contents.hhc");
		index.saveToFile(output);
		return output;
	}


	public File getOutputFile(final File outputFolder, final String namespace, final String identifier)
	{
		final File namespaceFolder = new File(outputFolder, namespace);
		return new File(namespaceFolder, identifier + ".html");
	}


	public StdNamespaceManager getNameSpaces()
	{
		return _namespaces;
	}


	public StdNamespace getNamespace(final String identifier)
	{
		return _namespaces.getNamespace(identifier);
	}
}
