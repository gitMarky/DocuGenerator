package project.marky.oc.docu.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import project.marky.oc.docu.ApplicationLogger;
import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.internal.interfaces.IProjectConfiguration;


@SuppressWarnings("serial") // No serialization intended
public class GenerateProjectButton extends JButton
{
	private final IProjectConfiguration _config;
	private GenerateProjectState _currentState = null;
	private GenerateProjectState _lastState = null;


	public GenerateProjectButton(final IProjectConfiguration config)
	{
		final String dist = "          ";
		setText(dist + "Generate HtmlHelp project!" + dist);
		setCurrentState(GenerateProjectState.NEEDS_CONFIG);
		addActionListener(new GenerateProjectListener(this));
		_config = config;
	}


	private GenerateProjectState getCurrentState()
	{
		return _currentState;
	}

	private void setCurrentState(final GenerateProjectState state)
	{
		// do an update
		if (!state.equals(_lastState))
		{
			_lastState = state;

			switch (state)
			{
				case NEEDS_CONFIG:
					setEnabled(false);
					setBackground(Color.DARK_GRAY);
					break;
				case READY:
					setEnabled(true);
					setBackground(Color.GREEN);
					break;
				case GENERATING:
					setEnabled(false);
					setBackground(Color.ORANGE);
					break;
				default:
					throw new IllegalStateException("This line of code should not be reached");
			}
		}

		_currentState = state;
	}


	private void onFinishedProjectGeneration()
	{
		ApplicationLogger.getLogger().info("Finished project generation");
		checkProjectStatus(true);
	}


	private class GenerateProjectListener implements ActionListener
	{
		private final GenerateProjectButton _button;

		private Thread _thread = null;

		private GenerateProjectListener(final GenerateProjectButton button)
		{
			_button = button;
		}

		@Override
		public void actionPerformed(final ActionEvent event)
		{
			if (event.getSource() == _button && _button.getCurrentState() == GenerateProjectState.READY)
			{
				generateProject();
			}
		}

		private void generateProject()
		{
			if (_thread != null)
			{
				ApplicationLogger.getLogger().warning("# Thread was unintentionally still alive");
				_thread.interrupt();
				_thread = null;
			}
			else
			{
				_thread = new Thread(new GenerateProjectTask(_button));
				_thread.run();
			}
		}
	}

	private class GenerateProjectTask implements Runnable
	{
		private final GenerateProjectButton _button;

		private GenerateProjectTask(final GenerateProjectButton button)
		{
			_button = button;
		}


		@Override
		public void run()
		{
			_button.setCurrentState(GenerateProjectState.GENERATING);
			new DocuGenerator().run(_config);
			_button.onFinishedProjectGeneration();
		}

	}


	private enum GenerateProjectState
	{
		NEEDS_CONFIG,
		READY,
		GENERATING;
	}


	public void checkProjectStatus()
	{
		checkProjectStatus(false);
	}


	private void checkProjectStatus(final boolean forceUpdate)
	{
		if (GenerateProjectState.GENERATING.equals(getCurrentState()) && !forceUpdate)
		{
			return;
		}

		final boolean hasTitle = _config.getTitle() != null;
		final boolean hasSource = _config.getSource() != null && _config.getSource().isDirectory();
		final boolean hasOutput = _config.getOutput() != null && _config.getOutput().isDirectory();
		final boolean hasStyle = _config.getStylesheet() != null && _config.getStylesheet().isFile();

		if (hasTitle && hasSource && hasOutput && hasStyle)
		{
			setCurrentState(GenerateProjectState.READY);
		}
		else
		{
			setCurrentState(GenerateProjectState.NEEDS_CONFIG);
		}
	}
}
