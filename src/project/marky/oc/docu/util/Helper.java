package project.marky.oc.docu.util;

import static project.marky.oc.docu.util.StringConstants.EMPTY_STRING;
import static project.marky.oc.docu.util.StringConstants.SPACE_STRING;

import java.io.File;
import java.io.IOException;

import project.marky.oc.docu.C4FileTypes;


public class Helper
{
	public static String[] splitIdentifierAndDocu(final String content)
	{
		String[] returnValue = new String[2];
		String[] split = content.split(SPACE_STRING);
		
		String identifier = null;
		String docu = null;
		
		for (int j = 0; j < split.length; j++)
		{
			if (split[j].equals(EMPTY_STRING) || split[j].equals(SPACE_STRING))
			{
				continue;
			}
			
			if (identifier == null)
			{
				identifier = split[j];
				continue;
			}
			
			if (docu == null)
			{
				docu = split[j];
			}
			else
			{
				docu += SPACE_STRING + split[j];
			}
		}
		
		returnValue[0] = identifier;
		returnValue[1] = docu;
		
		return returnValue;
	}
	
	public static String removeC4Extensions(final String text)
	{
		String transformed = text;
		
		for (C4FileTypes type : C4FileTypes.values())
		{
			transformed = transformed.replace(type.getExtension(), EMPTY_STRING);
		}
				
		return transformed;
	}
	
	public static String removeSpecialCharacters(final String text)
	{
		String[] specialCharacters = {"&"};
		
		String transformed = text;
		
		for (final String chr : specialCharacters)
		{
			transformed = transformed.replace(chr, EMPTY_STRING);
		}
		
		return transformed;
	}
	

	public static String reduceToAlphabet(final String text)
	{
		String[] specialCharacters = {"-", ">"};
		
		String transformed = text;
		
		for (final String chr : specialCharacters)
		{
			transformed = transformed.replace(chr, EMPTY_STRING);
		}
		
		return transformed;
	}
	
	public static boolean ensureCreateFile(final File file)
	{
		if (!file.exists())
		{
			file.getParentFile().mkdirs();
		}
		
		if (!file.exists())
		{
			try
			{
				return file.createNewFile();
			}
			catch (IOException e)
			{
				throw new IllegalArgumentException(e);
			}
		}
			
		return false;
	}
}
