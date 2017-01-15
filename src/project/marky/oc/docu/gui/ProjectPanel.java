package project.marky.oc.docu.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.marky.oc.docu.gui.fileChoosers.FileChooserCssFile;
import project.marky.oc.docu.gui.fileChoosers.FileChooserDirectory;
import project.marky.oc.docu.util.StyleConstants;

/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial")
// no serialization intended
public class ProjectPanel extends JPanel
{
	private final FileChooserDirectory _dir = new FileChooserDirectory(null);
	private final FileChooserCssFile _css = new FileChooserCssFile(null);
	final JTextField _titleField = new JTextField();


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

		this.add(assembleTitleRow());
		this.add(assembleButtonRow("Source", dir()));
		this.add(assembleButtonRow("Output", dir()));
		this.add(assembleButtonRow("Stylesheet", css()));
		this.add(assembleGenerateButton());
	}


	private Component assembleGenerateButton()
	{
		return new GenerateProjectButton();
	}


	private Component assembleTitleRow()
	{
		final JPanel panel = subPanel();
		final JLabel label = createLabel("Title");
		panel.add(label);
		panel.add(_titleField);
		return panel;
	}


	private Component assembleButtonRow(final String title, final JFileChooser fileChooser)
	{
		final JLabel label = createLabel(title);

		final JPanel panel = subPanel();
		panel.add(label);
		panel.add(new BrowseFilePanel(fileChooser));
		return panel;
	}


	private JLabel createLabel(final String title)
	{
		final JLabel label = new JLabel(title);
		label.setPreferredSize(StyleConstants.DIMENSION_PROJECT_LABEL);
		return label;
	}


	private JPanel subPanel()
	{
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		return panel;
	}


	private FileChooserDirectory dir()
	{
		return _dir;
	}


	private FileChooserCssFile css()
	{
		return _css;
	}


	// //////////////////////////////////////////////////////////////
	//
	// Public interface

	public String getTitle()
	{
		return _titleField.getText();
	}


	public void setTitle(final String title)
	{
		_titleField.setText(title);
	}

}
