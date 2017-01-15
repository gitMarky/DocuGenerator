package project.marky.oc.docu.gui.fileChoosers;

import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial") // no serialization intended
public class AbstractFileChooser extends JFileChooser
{
	/**
	 * Creates a file chooser with a default directory.
	 * 
	 * @param defaultDirectory the directory.
	 */
	AbstractFileChooser(final File defaultDirectory)
	{
		super();

		final boolean useDefault = (defaultDirectory == null || defaultDirectory.isDirectory());

		this.setCurrentDirectory(useDefault ? new File("") : defaultDirectory);
	}
}
