package project.marky.oc.docu.util;

import java.io.File;

import static project.marky.oc.docu.util.StringConstants.*;

public class RelFilePath
{
	public static String fromTo(final File source, final File destination)
	{
		File current;

		if (source.isFile())
		{
			current = source.getParentFile();
		}
		else if (source.isDirectory())
		{
			current = source;
		}
		else
			// if (!source.exists())
		{
			if (source.getAbsolutePath().contains(".")) // TODO: file or
				// directory
				current = source.getParentFile();
			else
				current = source;
		}
		String redirection = EMPTY_STRING;

		while (current != null && !destination.getAbsolutePath().contains(current.getAbsolutePath()))
		{
			current = current.getParentFile();
			redirection += "..\\";
		}

		String destinationPath;

		if (current != null)
		{
			destinationPath = destination.getAbsolutePath().replace(current.getAbsolutePath(), EMPTY_STRING);
		}
		else
		{
			destinationPath = destination.getAbsolutePath();
			redirection = "";
		}

		if (!redirection.equals(EMPTY_STRING))
		{
			if (destinationPath.length() > 1) destinationPath = destinationPath.substring(1); // .replace("\\",
			// EMPTY_STRING);
		}
		else if (destinationPath.startsWith("\\"))
		{
			if (destinationPath.length() > 1) destinationPath = destinationPath.substring(1); // .replace("\\",
			// EMPTY_STRING);
		}

		return redirection + destinationPath;
	}
}
