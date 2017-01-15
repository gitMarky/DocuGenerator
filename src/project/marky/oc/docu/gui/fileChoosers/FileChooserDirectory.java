package project.marky.oc.docu.gui.fileChoosers;

import java.io.File;

@SuppressWarnings("serial") // no serialization intended
public class FileChooserDirectory extends AbstractFileChooser
{
	/**
	 * Creates the file chooser.
	 * 
	 * @param defaultDirectory see {@link AbstractFileChooser#AbstractFileChooser(File)}.
	 */
	public FileChooserDirectory(final File defaultDirectory)
	{
		super(defaultDirectory);
		this.setFileSelectionMode(DIRECTORIES_ONLY);
	}
}
