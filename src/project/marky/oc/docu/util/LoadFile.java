package project.marky.oc.docu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import project.marky.oc.docu.ApplicationLogger;


/**
 * Helper for loading files.
 */
public final class LoadFile
{
	private LoadFile()
	{
		// prevent instantiation of utility class
	}


	/**
	 * Gets the contents of a file.
	 * 
	 * @param file the input file.
	 * @return the contents, as a string.
	 */
	public static String getFileContent(final File file)
	{
		try
		{
			return new Scanner(file).useDelimiter("\\Z").next();
		}
		catch (final FileNotFoundException e)
		{
			ApplicationLogger.getLogger().throwing("File not found", "Will return empty string", e);
			return "";
		}
	}

}
