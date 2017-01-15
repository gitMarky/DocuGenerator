package project.marky.oc.docu.scripts;

import java.io.File;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.ProjectConfiguration;

/**
 * Generates the docu for a specific project.
 * 
 * @deprecated Contains hardcoded file paths, should be replaced with a file
 *             selector.
 */
@Deprecated
public class ProjectOCUnrealArena
{
	private static final File _workspace = new File("C:\\Editing\\Clonk\\OpenClonk_Development\\UnrealArena.ocf");
	private static final File _outputFolder = new File("C:\\Editing\\Clonk\\HtmlHelp\\UnrealArena\\sdk");
	private static final File _stylesheet = new File("C:\\Editing\\Clonk\\HtmlHelp\\UnrealArena\\docu.css");


	public static void main(final String[] args)
	{
		final DocuGenerator parser = new DocuGenerator();
		parser.run(new ProjectConfiguration("Unreal Arena", _workspace, _outputFolder, _stylesheet));
	}
}
