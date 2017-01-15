package project.marky.oc.docu.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.marky.oc.docu.IProjectConfiguration;
import project.marky.oc.docu.gui.fileChoosers.FileChooserCssFile;
import project.marky.oc.docu.gui.fileChoosers.FileChooserDirectory;
import project.marky.oc.docu.util.StyleConstants;

/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial")
// no serialization intended
public class ProjectPanel extends JPanel implements IProjectConfiguration
{
	private final FileChooserDirectory _dir = new FileChooserDirectory(null);
	private final FileChooserCssFile _css = new FileChooserCssFile(null);
	final JTextField _titleField = new JTextField();

	final BrowseFilePanel _source;
	final BrowseFilePanel _output;
	final BrowseFilePanel _stylesheet;

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

		_source = new BrowseFilePanel(dir());
		_output = new BrowseFilePanel(dir());
		_stylesheet = new BrowseFilePanel(css());

		this.add(assembleTitleRow());
		this.add(assembleButtonRow("Source", _source));
		this.add(assembleButtonRow("Output", _output));
		this.add(assembleButtonRow("Stylesheet", _stylesheet));
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


	private Component assembleButtonRow(final String title, final BrowseFilePanel fileChooser)
	{
		final JLabel label = createLabel(title);

		final JPanel panel = subPanel();
		panel.add(label);
		panel.add(fileChooser);
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

	@Override
	public String getTitle()
	{
		return _titleField.getText();
	}


	public void setTitle(final String title)
	{
		_titleField.setText(title);
	}


	@Override
	public File getSource()
	{
		return _source.getFile();
	}


	@Override
	public File getOutput()
	{
		return _output.getFile();
	}


	@Override
	public File getStylesheet()
	{
		return _stylesheet.getFile();
	}
}
