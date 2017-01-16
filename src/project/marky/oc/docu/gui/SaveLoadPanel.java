package project.marky.oc.docu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import project.marky.oc.docu.DocuGui;
import project.marky.oc.docu.gui.fileChoosers.FileChooserXmlFile;
import project.marky.oc.docu.util.Constants;

/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial") // no serialization intended
public class SaveLoadPanel extends JPanel
{
	private final JButton _loadButton = new JButton("Load Project");
	private final JButton _saveButton = new JButton("Save Project");
	private final FileChooserXmlFile _dir = new FileChooserXmlFile(Constants.PROJECTS);
	private final DocuGui _gui;

	public SaveLoadPanel(final DocuGui gui)
	{
		super();

		this.setBorder(BorderFactory.createTitledBorder("Current Configuration"));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final ActionListener listener = new SaveLoadListener();
		_saveButton.addActionListener(listener);
		_loadButton.addActionListener(listener);

		this.add(_loadButton);
		this.add(_saveButton);
		_gui = gui;
	}


	private class SaveLoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent action)
		{
			if (action.getSource() == _saveButton)
			{
				// do something
				final int choice = _dir.showSaveDialog(SaveLoadPanel.this);

				if (choice == JFileChooser.APPROVE_OPTION)
				{
					final File file = _dir.getSelectedFile();
					saveProject(file);
				}
			}
			else if (action.getSource() == _loadButton)
			{
				// do something else
				final int choice = _dir.showOpenDialog(SaveLoadPanel.this);

				if (choice == JFileChooser.APPROVE_OPTION)
				{
					final File file = _dir.getSelectedFile();
					loadProject(file);
				}

			}
		}

		private void saveProject(final File file)
		{
			if (_gui != null)
			{
				_gui.saveSpecificProject(file);
			}
		}

		private void loadProject(final File file)
		{
			if (_gui != null)
			{
				_gui.loadSpecificProject(file);
			}
		}
	}
}
