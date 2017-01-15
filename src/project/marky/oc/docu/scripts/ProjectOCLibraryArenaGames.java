package project.marky.oc.docu.scripts;

import java.io.File;

import project.marky.oc.docu.DocuGenerator;

/**
 * Generates the docu for a specific project.
 * 
 * @deprecated Contains hardcoded file paths, should be replaced with a file
 *             selector.
 */
@Deprecated
public class ProjectOCLibraryArenaGames
{
	private static final File _workspace = new File("C:\\Editing\\Clonk\\OpenClonk_Development\\LibraryArenaGames.ocd");
	private static final File _outputFolder = new File("C:\\Editing\\Clonk\\HtmlHelp\\LibraryArenaGames\\sdk");
	private static final File _stylesheet = new File("C:\\Editing\\Clonk\\HtmlHelp\\LibraryArenaGames\\docu.css");


	public static void main(final String[] args)
	{
		final DocuGenerator parser = new DocuGenerator();
		parser.run(_workspace, _outputFolder, _stylesheet, "Library: Arena Games");
	}
}
