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
public class ProjectOCLibraryShooter
{
	public static void main(final String[] args)
	{
		final DocuGenerator parser = new DocuGenerator();
		parser.run(ProjectConfiguration.loadFromXml(new File("projects\\project_library_shooter.xml")));
	}
}
