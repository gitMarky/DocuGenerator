package project.marky.oc.docu.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.marky.oc.docu.gui.fileChoosers.FileChooserDirectory;


/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial") // no serialization intended
public class ProjectPanel extends JPanel
{
	private final FileChooserDirectory _dir = new FileChooserDirectory(null);


	public ProjectPanel()
	{
		super();

		// set default file for chooser
		if (dir().getSelectedFile() == null)
		{
			dir().setCurrentDirectory(new File(""));
		}

		this.setBorder(BorderFactory.createTitledBorder("Project"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(assembleButtonRow("Source"));
		this.add(assembleButtonRow("Output"));
	}

	private Component assembleButtonRow(final String title)
	{
		final JLabel label = new JLabel(title);
		label.setPreferredSize(new Dimension(50, 0));

		final JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(label);
		panel.add(new BrowseFilePanel(dir()));
		return panel;
	}

	private FileChooserDirectory dir()
	{
		return _dir;
	}

	////////////////////////////////////////////////////////////////
	//
	// Handle actions


}
