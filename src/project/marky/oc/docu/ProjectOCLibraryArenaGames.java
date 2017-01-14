package project.marky.oc.docu;

import java.io.File;

public class ProjectOCLibraryArenaGames
{
	private static final File _workspace = new File("C:\\Editing\\Clonk\\OpenClonk_Development\\LibraryArenaGames.ocd");
	private static final File _outputFolder = new File("C:\\Editing\\Clonk\\HtmlHelp\\LibraryArenaGames\\sdk");
	private static final File _stylesheet = new File("C:\\Editing\\Clonk\\HtmlHelp\\LibraryArenaGames\\docu.css");
	
	//private static final File _outputHHC = new File(_outputFolder, "contents.hhc");
	//private static final File _outputHHK = new File(_outputFolder, "index.hhk");
	//private static final File _outputHHP = new File(_outputFolder, "project.hhp");
	
	public static void main(String[] args)
	{
		C4FileParser parser = new C4FileParser();
		parser.run(_workspace, _outputFolder, _stylesheet, "Library: Arena Games");
	}
}
