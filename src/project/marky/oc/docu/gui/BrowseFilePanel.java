package project.marky.oc.docu.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial") // no serialization intended
public class BrowseFilePanel extends JPanel implements ActionListener
{
	private final JFileChooser _dir;
	private final JButton _button = new JButton("Browse");
	private File _file;
	private final JTextField _text = new JTextField();

	public BrowseFilePanel(final JFileChooser chooser)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		_text.setPreferredSize(new Dimension(100, 0));
		_text.setEnabled(false);

		_dir = chooser;
		_button.addActionListener(this);

		this.add(_text);
		this.add(_button);

		setFile(chooser.getSelectedFile());
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _button)
		{
			final int choice = _dir.showOpenDialog(this);

			if (choice == JFileChooser.APPROVE_OPTION)
			{
				final File file = _dir.getSelectedFile();
				setFile(file);
			}
		}
	}

	private void setFile(final File file)
	{
		_file = file == null ? new File("") : file;
		_text.setText(getFile().getAbsolutePath());
	}

	public File getFile()
	{
		return _file;
	}
}
