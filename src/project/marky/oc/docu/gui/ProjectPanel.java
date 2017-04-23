package project.marky.oc.docu.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.marky.oc.docu.ApplicationLogger;
import project.marky.java.gui.BrowseFilePanel;
import project.marky.java.gui.IFileSelectionListener;
import project.marky.java.gui.fileChooser.FileChooserCssFile;
import project.marky.java.gui.fileChooser.FileChooserDirectory;
import project.marky.oc.docu.internal.ProjectConfiguration;
import project.marky.oc.docu.internal.interfaces.IProjectConfiguration;
import project.marky.oc.docu.util.StyleConstants;

/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial")
// no serialization intended
public class ProjectPanel extends JPanel implements IProjectConfiguration, IFileSelectionListener
{
	private final FileChooserDirectory _dir = new FileChooserDirectory(new File("projects"));
	private final FileChooserCssFile _css = new FileChooserCssFile(new File("projects"));
	final JTextField _titleField = new JTextField();

	final BrowseFilePanel _source;
	final BrowseFilePanel _output;
	final BrowseFilePanel _stylesheet;
	private final GenerateProjectButton _generateButton;

	public ProjectPanel()
	{
		super();

		this.setBorder(BorderFactory.createTitledBorder("Project"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		_source = new BrowseFilePanel(dir());
		_output = new BrowseFilePanel(dir());
		_stylesheet = new BrowseFilePanel(css());
		_generateButton = new GenerateProjectButton(this);

		_source.addFileSelectionListener(this);
		_output.addFileSelectionListener(this);
		_stylesheet.addFileSelectionListener(this);

		this.add(assembleTitleRow());
		this.add(assembleButtonRow("Source", _source));
		this.add(assembleButtonRow("Output", _output));
		this.add(assembleButtonRow("Stylesheet", _stylesheet));
		this.add(assembleGenerateButton());
	}


	private Component assembleGenerateButton()
	{
		return _generateButton;
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


	private void setSource(final File file)
	{
		_source.setFile(file);
	}


	@Override
	public File getOutput()
	{
		return _output.getFile();
	}


	private void setOutput(final File file)
	{
		_output.setFile(file);
	}


	@Override
	public File getStylesheet()
	{
		return _stylesheet.getFile();
	}


	private void setStylesheet(final File file)
	{
		_stylesheet.setFile(file);
	}


	@Override
	public void onFileSelection(final ActionEvent e)
	{
		if (e.getSource() == _source || e.getSource() == _output)
		{
			css().setCurrentDirectory(dir().getCurrentDirectory());
		}
		if (e.getSource() == _stylesheet)
		{
			dir().setCurrentDirectory(css().getCurrentDirectory());
		}

		_generateButton.checkProjectStatus();
	}


	public void loadConfigFile(final File file)
	{
		ApplicationLogger.getLogger().info("Loading project configuration from file: " + file.getAbsolutePath());
		final IProjectConfiguration project = ProjectConfiguration.loadFromXml(file);

		setTitle(project.getTitle());
		setSource(project.getSource());
		setOutput(project.getOutput());
		setStylesheet(project.getStylesheet());
	}


	public void saveConfigFile(final File file)
	{
		ApplicationLogger.getLogger().info("Saving project configuration to file: " + file.getAbsolutePath());
		ProjectConfiguration.saveToXmlFile(this, file);
	}
}
