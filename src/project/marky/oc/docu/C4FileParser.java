package project.marky.oc.docu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.marky.oc.docu.c4script.C4DocuParser;
import project.marky.oc.docu.c4script.C4FuncParser;
import project.marky.oc.docu.c4script.C4ScriptFileParser;
import project.marky.oc.docu.external.htmlHelp.HtmlHelpContents;
import project.marky.oc.docu.external.htmlHelp.HtmlHelpIndex;
import project.marky.oc.docu.external.htmlHelp.HtmlHelpProject;
import project.marky.oc.docu.logic.DocuPage;
import project.marky.oc.docu.logic.StdNamespace;
import project.marky.oc.docu.logic.StdNamespaceManager;
import project.marky.oc.docu.util.RelFilePath;

public class C4FileParser
{
	private final StdNamespaceManager _namespaces;
	
	public C4FileParser()
	{
		_namespaces = new StdNamespaceManager();
	}
	
	public void run(final File inputFolderProject, final File outputFolderProject, final File cssStyleSheet, final String title)
	{
		parseScriptsAndDefcore(inputFolderProject);
		
		createHtmlFiles(outputFolderProject, cssStyleSheet);
		
		final File defaultFile = getOutputFile(outputFolderProject, _namespaces.getNamespaceDocu().getIdentifier(), "index");
		
		final File index = createIndexFile(outputFolderProject);
		final File contents = createContentsFile(outputFolderProject, defaultFile, title);
		createProjectFile(outputFolderProject, contents, index, title, null);
	}

	private ArrayList<File> parseScriptsAndDefcore(File sourceFolder)
	{
		ArrayList<File> defCoreFiles = new ArrayList<File>();
		ArrayList<File> scriptFiles = new ArrayList<File>();
		ArrayList<File> docuFiles = new ArrayList<File>();
		
		parseFolder(sourceFolder, defCoreFiles, docuFiles, scriptFiles);
		
		ApplicationLogger.getLogger().info("DefCore Files:");
		
		for (final File file : defCoreFiles)
		{
			ApplicationLogger.getLogger().info(" * " + file.getAbsolutePath());
			parseDefcore(file);
		}
		
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
		
		return null;
	}
	
	private void parseScriptFile(File file, final boolean addDocuToDocuNamespace)
	{
		C4ScriptFileParser parsedScript = C4ScriptFileParser.parseFile(file);
				
		StdNamespace space = _namespaces.getNamespace(parsedScript.getOrigin());
		
		if (space == null)
		{
			System.err.println(" * > cannot add to namespace with origin '" + parsedScript.getOrigin() + "'");
		}


		for (final C4DocuParser docu : parsedScript.getHeaderList())
		{
			DocuPage page = new DocuPage(docu);
			
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
		
		for (final Entry<C4DocuParser, C4FuncParser> entry : parsedScript.getFunctionList().entrySet())
		{
			C4FuncParser function = entry.getValue();
			DocuPage page = new DocuPage(entry.getKey(), function);
			
			if (function.getFunctionVisibility() != null && function.getFunctionVisibility().equals(StdNamespace.NAMESPACE_GLOBAL))
			{
				addPageToNamespace(page, _namespaces.getNamespaceGlobal(), function.getFunctionName());
			}
			else
			{
				if (space != null)
				{
					addPageToNamespace(page, space, function.getFunctionName());
				}
			}
		}
	}
	
	private void addPageToNamespace(final DocuPage page, final StdNamespace space, final String identifier)
	{
		space.add(page);
		ApplicationLogger.getLogger().info(" * > added " + space.getIdentifier() + "#" + identifier);
	}

	@SuppressWarnings ("resource") private void parseDefcore(File file)
	{
		try
		{
			String content = new Scanner(file).useDelimiter("\\Z").next();
			
			//ApplicationLogger.getLogger().info(content);
			
			Pattern patternID = Pattern.compile("id=(.+)");
			Matcher matcherID = patternID.matcher(content);
			
			if (matcherID.find())
			{
				final String definition = matcherID.group(0).replace("id=", "");
			    
				Pattern patternName = Pattern.compile("Name=(.+)");
				Matcher matcherName = patternName.matcher(content);
				
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
				
				StdNamespace namespace = new StdNamespace(definition, name, file.getParentFile());
				_namespaces.addNamespace(namespace);
			}
			else
			{
			    ApplicationLogger.getLogger().info(" * > no ID found");
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private void parseFolder(File sourceFolder, ArrayList<File> defCoreFiles, ArrayList<File> docuFiles, ArrayList<File> scriptFiles)
	{
		if (sourceFolder.isFile())
		{
			if (sourceFolder.getName().toLowerCase().equals("script.c") || sourceFolder.getParentFile().getName().toLowerCase().contains("system."))
			{
				scriptFiles.add(sourceFolder);
			}
			else  if (sourceFolder.getName().endsWith(".c"))
			{
				docuFiles.add(sourceFolder);
			}
			else if (sourceFolder.getName().toLowerCase().equals("defcore.txt"))
			{
				defCoreFiles.add(sourceFolder);
			}
		}
		else if (sourceFolder.isDirectory())
		{
			if (sourceFolder.getName().endsWith(".ocs") || sourceFolder.getName().endsWith(".c4s")) return;
			
			File[] subFiles = sourceFolder.listFiles();
			
			for (final File file : subFiles)
			{
				parseFolder(file, defCoreFiles, docuFiles, scriptFiles);
			}
		}
	}
	
	private void createHtmlFiles(File outputFolderProject, File cssStyleSheet)
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
	
	private void createProjectFile(File outputFolderProject, final File contents, final File index, final String title, final File defaultFile)
	{
		HtmlHelpProject file = new HtmlHelpProject(contents, index, title, defaultFile);
		file.saveToFile(new File(outputFolderProject, "project.hhp"));
	}
	
	private File createIndexFile(File outputFolderProject)
	{
		HashMap<String, File> map = new HashMap<String, File>();
		
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
	
	private File createContentsFile(File outputFolderProject, File defaultFile, String title)
	{		
		final HtmlHelpContents index = new HtmlHelpContents(this);
		index.createContent(outputFolderProject, defaultFile, title);
		
		final File output = new File(outputFolderProject, "contents.hhc");
		index.saveToFile(output);
		return output;
	}
	
	public File getOutputFile(File outputFolder, String namespace, String identifier)
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
