package project.marky.oc.docu.external.htmlHelp;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.html.StdHtmlFile;
import project.marky.oc.docu.logic.DocuPage;
import project.marky.oc.docu.logic.StdNamespace;
import project.marky.oc.docu.util.StringConstants;

/**
 * HtmlHelp table of contents.
 *
 */
public class HtmlHelpContents extends StdHtmlFile
{
	private static final String CLOSE_TAG = "\">";
	private static final String _UL = "</UL>";
	private static final String UL = "<UL>";
	private static final String OBJECT = "     </OBJECT>";
	private static final String PARAM_NAME_LOCAL_VALUE = "     <param name=\"Local\" value=\"";
	private static final String PARAM_NAME_NAME_VALUE = "     <param name=\"Name\" value=\"";
	private static final String LI_OBJECT_TYPE_TEXT_SITEMAP = "<LI> <OBJECT type=\"text/sitemap\">";
	final DocuGenerator _parser;

	public HtmlHelpContents(final DocuGenerator c4FileParser)
	{
		_parser = c4FileParser;
	}

	private void writeHeader()
	{
		writeLine("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">");
		writeLine("<HTML>");
		writeLine("<HEAD>");
		writeLine("<meta name=\"GENERATOR\" content=\"Microsoft&reg; HTML Help Workshop 4.1\">");
		writeLine("");
		writeLine("<!-- Notice: some tags and tab positions are hardcoded in DokuCompile/compilefns.exe -->");
		writeLine("");
		writeLine("</HEAD><BODY>");
		writeLine("<OBJECT type=\"text/site properties\">");
		writeLine("	<param name=\"Window Styles\" value=\"0x800025\">");
		writeLine(OBJECT);
		writeLine(UL);
	}
	
	private void writeFooter()
	{
		writeLine(_UL);
		writeLine("</BODY></HTML>");
		writeLine("");
	}
	
	private void writeEntries(final File outputFolder, File defaultFile)
	{
		//final String script_namespace = _parser.getNameSpaces().getNamespaceGlobal().getIdentifier(); 
		//final String docu_namespace = _parser.getNameSpaces().getNamespaceDocu().getIdentifier(); 
		final String category_objects = "Objects";
		final String category_script = "Script";
		
		final int level = 1;
		
		writeLine(LI_OBJECT_TYPE_TEXT_SITEMAP, level);
		writeLine(PARAM_NAME_NAME_VALUE + category_objects + CLOSE_TAG, level);
		writeLine(PARAM_NAME_LOCAL_VALUE + defaultFile.getAbsolutePath() + CLOSE_TAG, level);
		writeLine(OBJECT, level);
		writeLine(UL, level);
		
		
		ArrayList<String> spaces = new ArrayList<String>();
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (int i = 0; i < _parser.getNameSpaces().getNamespaces().size(); i++)
		{
			final String space = _parser.getNameSpaces().getNamespaces().get(i).getIdentifier();
			map.put(space, i);
			spaces.add(space);
		}
		
		Collections.sort(spaces);
		
		
		for (final String space : spaces)
		//for (final StdNamespace namespace : _parser.getNameSpaces().getNamespaces())
		{
			final StdNamespace namespace = _parser.getNamespace(space);
			
			if (isGlobalNamespace(namespace) || isDocuNamespace(namespace)) continue;
			
			writeNamespaceEntries(outputFolder, namespace);
		}
		
		writeLine(_UL, level);

		writeLine(LI_OBJECT_TYPE_TEXT_SITEMAP, level);
		writeLine(PARAM_NAME_NAME_VALUE + category_script + CLOSE_TAG, level);
		writeLine(PARAM_NAME_LOCAL_VALUE + defaultFile.getAbsolutePath() + CLOSE_TAG, level);
		writeLine("	</OBJECT>", level);
		writeLine(UL, level);
		
		for (final String space : spaces)
		//for (final StdNamespace namespace : _parser.getNameSpaces().getNamespaces())
		{
			final StdNamespace namespace = _parser.getNamespace(space);
			
			if (!isGlobalNamespace(namespace)) continue;
			
			writeNamespaceEntries(outputFolder, namespace);
		}
		
		writeLine(_UL, level);
	}
	
	private void writeNamespaceEntries(final File outputFolder, StdNamespace namespace)
	{
		final String name = namespace.getIdentifier();
		
		final File location = _parser.getOutputFile(outputFolder, namespace.getIdentifier(), "index");
		final File namespaceFolder = location.getParentFile();
		
		if (!namespaceFolder.isDirectory()) return;
		
		final int level = 2;
		
		final boolean writeHeader = !isGlobalNamespace(namespace) && !isDocuNamespace(namespace);
		
		if (writeHeader)
		{
			writeLine(LI_OBJECT_TYPE_TEXT_SITEMAP, level);
			writeLine(PARAM_NAME_NAME_VALUE + name + CLOSE_TAG, level);
			if (location.exists())
			{
				writeLine(PARAM_NAME_LOCAL_VALUE + location.getAbsolutePath() + CLOSE_TAG, level);
			}
			writeLine(OBJECT, level);
			writeLine(UL, level);
		}
		
		ArrayList<String> pages = new ArrayList<String>();
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (int i = 0; i < namespace.getPages().size(); i++)
		{
			final String page = namespace.getPages().get(i).getIdentifier();
			map.put(page, i);
			pages.add(page);
		}
		
		Collections.sort(pages);
		
		for (final String page : pages)
		{
			final DocuPage docuPage = namespace.getPages().get(map.get(page));
			if (docuPage.getIdentifier().equals("index")) continue;
			
			writePageEntries(outputFolder, namespace, docuPage);
		}
		
		if (writeHeader)
		writeLine(_UL, level);
	}

	private boolean isGlobalNamespace(StdNamespace namespace)
	{
		return _parser.getNameSpaces().getNamespaceGlobal().getIdentifier().equals(namespace.getIdentifier());
	}

	private boolean isDocuNamespace(StdNamespace namespace)
	{
		return _parser.getNameSpaces().getNamespaceDocu().getIdentifier().equals(namespace.getIdentifier());
	}

	private void writePageEntries(final File outputFolder, StdNamespace namespace, DocuPage page)
	{
		final int level = 3;
		final File destination = _parser.getOutputFile(outputFolder, namespace.getIdentifier(), page.getIdentifier());
		
		writeLine(LI_OBJECT_TYPE_TEXT_SITEMAP, level);
		writeLine(PARAM_NAME_NAME_VALUE + page.getIdentifier() + CLOSE_TAG, level);
		writeLine(PARAM_NAME_LOCAL_VALUE + destination.getAbsolutePath() + CLOSE_TAG, level);
		writeLine(OBJECT, level);
	}

	public void createContent(final File outputFolder, File defaultFile, String title)
	{
		writeHeader();
		
		// default file
		writeLine(LI_OBJECT_TYPE_TEXT_SITEMAP);
		writeLine(PARAM_NAME_NAME_VALUE + title + CLOSE_TAG);
		writeLine(PARAM_NAME_LOCAL_VALUE + defaultFile.getAbsolutePath() + CLOSE_TAG);
		writeLine(OBJECT);
		writeLine(UL);
		
		writeEntries(outputFolder, defaultFile);
		
		writeLine(_UL);
		
		writeFooter();
	}
	
	private void writeLine(final String line, final int indent)
	{
		for (int i = 0; i < indent; i++)
		{
			write(StringConstants.TAB_SPACE);
		}
		writeLine(line);
	}
}
