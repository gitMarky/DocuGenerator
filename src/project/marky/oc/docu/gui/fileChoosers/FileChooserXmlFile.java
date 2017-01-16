package project.marky.oc.docu.gui.fileChoosers;

import java.io.File;

import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial") // no serialization intended
public class FileChooserXmlFile extends AbstractFileChooser
{
	/**
	 * Creates the file chooser.
	 * 
	 * @param defaultDirectory see {@link AbstractFileChooser#AbstractFileChooser(File)}.
	 */
	public FileChooserXmlFile(final File defaultDirectory)
	{
		super(defaultDirectory);
		this.setFileSelectionMode(FILES_ONLY);

		final FileFilter filter = new javax.swing.filechooser.FileFilter()
		{
			@Override
			public boolean accept(final File file)
			{
				if (file == null)
				{
					throw new IllegalArgumentException("Parameter 'file' must not be null.");
				}

				return file.isDirectory() || file.getName().toLowerCase().endsWith(".xml");
			}


			@Override
			public String getDescription()
			{
				return ".xml files";
			}
		};

		this.setFileFilter(filter);
	}
}
