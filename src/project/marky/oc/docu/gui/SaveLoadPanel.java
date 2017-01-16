package project.marky.oc.docu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial") // no serialization intended
public class SaveLoadPanel extends JPanel
{
	private final JButton _loadButton = new JButton("Load Project");
	private final JButton _saveButton = new JButton("Save Project");

	public SaveLoadPanel()
	{
		super();

		this.setBorder(BorderFactory.createTitledBorder("Current Configuration"));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final ActionListener listener = new SaveLoadListener();
		_saveButton.addActionListener(listener);
		_loadButton.addActionListener(listener);

		this.add(_loadButton);
		this.add(_saveButton);
	}


	private class SaveLoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent action)
		{
			if (action.getSource() == _saveButton)
			{
				// do something
			}
			else if (action.getSource() == _loadButton)
			{
				// do something else
			}
		}
	}
}
