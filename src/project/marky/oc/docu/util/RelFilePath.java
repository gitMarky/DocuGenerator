package project.marky.oc.docu.util;

import java.io.File;

import project.marky.oc.docu.ApplicationLogger;
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
		else // if (!source.exists())
		{
			if (source.getAbsolutePath().contains(".")) // TODO: file or directory 
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
			destinationPath =  destination.getAbsolutePath();
			redirection = "";
		}
		
		if (!redirection.equals(EMPTY_STRING))
		{
			if (destinationPath.length() > 1)
			destinationPath = destinationPath.substring(1); //.replace("\\", EMPTY_STRING);
		}
		else if (destinationPath.startsWith("\\"))
		{
			if (destinationPath.length() > 1)
			destinationPath = destinationPath.substring(1); //.replace("\\", EMPTY_STRING);
		}
		
		return redirection + destinationPath;
	}
	
	public static void main(String[] args)
	{
		File a = new File("C:\\Test\\Bla\\Bli\\Blu\\");
		File b = new File(a, "folder1\\file.extension");
		File c = new File(a, "folder2\\file.extension");
		
		ApplicationLogger.getLogger().info("" + fromTo(b, c));
		
		b = new File(a, "folder1\\folder2\\folder3\\file.extension");
		c = new File(a, "folder2\\file.extension");
		
		ApplicationLogger.getLogger().info("" + fromTo(b, c));


		b = new File(a, "folder1\\folder2\\folder3\\file.extension");
		c = new File("E:\\Test");

		ApplicationLogger.getLogger().info("" + fromTo(b, c));

		c = new File(a, "folder1\\folder2\\folder3\\file.extension");
		b = new File(a, "folder1\\folder2\\testfile.extension");

		ApplicationLogger.getLogger().info("" + fromTo(b, c));
		
		File workspace = new File("C:\\Editing\\Clonk\\eclipseworkspace");
		File project = new File(workspace, "RPGLibrary\\RPGLibrary.c4d");
		File script = new File(project, "Libraries L.c4d\\Functionalities F.c4d\\DayNightCycle _C.c4d\\Script.c");

		ApplicationLogger.getLogger().info("" + fromTo(project, script));
		
	}
}
