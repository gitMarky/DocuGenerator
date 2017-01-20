package project.marky.oc.docu.internal.parsers;

import java.io.File;

/**
 * Collection of files.
 */
class Files
{
	static final File FILE_WITH_HEADER = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuHeader.txt");
	static final File FILE_NO_HEADER = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuNoHeader.txt");
	static final File FILE_FUNCTIONS = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuFunctions.txt");

	private Files()
	{
		// prevent instantiation
	}
}
