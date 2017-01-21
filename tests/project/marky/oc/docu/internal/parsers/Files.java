package project.marky.oc.docu.internal.parsers;

import java.io.File;

import project.marky.oc.docu.util.LoadFile;

/**
 * Collection of files.
 */
class Files
{
	static final File FILE_WITH_HEADER = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuHeader.txt");
	static final File FILE_NO_HEADER = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuNoHeader.txt");
	static final File FILE_FUNCTIONS = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuFunctions.txt");
	static final File FILE_ALL_TAGS = new File("tests\\project\\marky\\oc\\docu\\internal\\parsers\\resources\\DocuWithAllTags.txt");

	static final String CONTENT_WITH_HEADER = LoadFile.getFileContent(Files.FILE_WITH_HEADER);
	static final String CONTENT_NO_HEADER = LoadFile.getFileContent(Files.FILE_NO_HEADER);
	static final String CONTENT_FUNCTIONS = LoadFile.getFileContent(Files.FILE_FUNCTIONS);
	static final String CONTENT_ALL_TAGS = LoadFile.getFileContent(Files.FILE_ALL_TAGS);

	private Files()
	{
		// prevent instantiation
	}
}
