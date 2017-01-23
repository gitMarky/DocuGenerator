package project.marky.oc.docu.external.htmlHelp;

import static project.marky.oc.docu.util.StringConstants.TAB_STRING;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import project.marky.oc.docu.ApplicationLogger;
import project.marky.oc.docu.html.StdHtmlFile;
import project.marky.oc.docu.util.RelFilePath;

/**
 * HtmlHelp index.
 * 
 */
public class HtmlHelpIndex extends StdHtmlFile
{
	final HashMap<String, File> _map;


	public HtmlHelpIndex(final HashMap<String, File> map)
	{
		_map = map;
	}


	protected void writeHeader()
	{
		writeLine("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">");
		writeLine("<HTML>");
		writeLine("<HEAD>");
		writeLine("<meta name=\"GENERATOR\" content=\"Microsoft&reg; HTML Help Workshop 4.1\">");
		writeLine("<!-- Sitemap 1.0 -->");
		writeLine("</HEAD><BODY>");
		writeLine("<UL>");
	}


	protected void writeFooter()
	{
		writeLine("</UL>");
		writeLine("</BODY></HTML>");
	}


	public void createContent(final File outputFolder)
	{
		final String indent = TAB_STRING;

		writeHeader();

		final ArrayList<String> names = new ArrayList<String>(_map.keySet());

		ApplicationLogger.getLogger().info("Index names: " + names);

		Collections.sort(names);

		for (final String name : names)
		{
			final File file = _map.get(name);

			writeLine(indent + "<LI> <OBJECT type=\"text/sitemap\">");
			writeLine(indent + "	<param name=\"Name\" value=\"" + name + "\">");
			writeLine(indent + "	<param name=\"Local\" value=\"" + RelFilePath.fromTo(outputFolder, file) + "\">");
			writeLine(indent + "    </OBJECT>");
		}

		writeFooter();
	}
}
