package project.marky.oc.docu;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.marky.oc.docu.gui.ProjectPanel;
import project.marky.oc.docu.gui.SaveLoadPanel;
import project.marky.oc.docu.util.Constants;
import project.marky.oc.docu.util.StyleConstants;


/**
 * Frontend for the user.
 */
public class DocuGui
{
	private static DocuGui GUI;

	/////////////////////////////////////////////////////////////////////////////////////////////////////7
	//
	// Main method

	/**
	 * Main method that should be used for launching the application.
	 * 
	 * @param args the parameters. Should be empty.
	 */
	public static void main(final String[] args)
	{
		// declarations

		final Runnable mainTask = new Runnable()
		{
			@Override
			public void run()
			{
				ApplicationLogger.getLogger().info(Constants.HLINE);
				ApplicationLogger.getLogger().info("Initializing");
				ApplicationLogger.getLogger().info(Constants.HLINE);

				GUI = new DocuGui();

				ApplicationLogger.getLogger().info(Constants.HLINE);
				ApplicationLogger.getLogger().info("Preparation phase is over, from here on the user takes over.");
				ApplicationLogger.getLogger().info(Constants.HLINE);
			}
		};

		final Runnable shutdownTask = new Runnable()
		{
			@Override
			public void run()
			{
				ApplicationLogger.getLogger().info(Constants.HLINE);
				ApplicationLogger.getLogger().info("Shutting down");
				ApplicationLogger.getLogger().info(Constants.HLINE);

				if (GUI != null)
				{
					GUI.saveLatestProject();
				}
			}
		};

		final Thread shutdownHook = new Thread(shutdownTask);
		Runtime.getRuntime().addShutdownHook(shutdownHook);

		// actual main method

		try
		{
			new Thread(mainTask).start();
		}
		catch (final Exception e)
		{
			ApplicationLogger.getLogger().info("Caught exception");
			ApplicationLogger.getLogger().throwing("", "", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////7
	//
	// Actual class


	private final JFrame _frame = new JFrame();
	private final ProjectPanel _project = new ProjectPanel();


	private DocuGui()
	{
		ApplicationLogger.getLogger().info(Constants.HLINE);
		ApplicationLogger.getLogger().info("Setup: Creating GUI");
		ApplicationLogger.getLogger().info(Constants.HLINE);

		final JPanel panel = new JPanel();
		panel.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);

		_frame.add(panel);
		_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		assembleGui(panel);

		_frame.pack();
		_frame.setVisible(true);

		loadLatestProject();
	}


	private void assembleGui(final JPanel mainPanel)
	{
		final SaveLoadPanel saveLoadPanel = new SaveLoadPanel(this);
		//saveLoadPanel.setPreferredSize(_project.getPreferredSize());

		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setLayout(new BorderLayout());
		//mainPanel.add(saveLoadPanel);
		mainPanel.add(saveLoadPanel, BorderLayout.PAGE_START);
		mainPanel.add(_project, BorderLayout.PAGE_END);
		//_frame.pack();
		//saveLoadPanel.setPreferredSize(_project.getSize());
	}


	private void loadLatestProject()
	{
		ApplicationLogger.getLogger().info("Loading project from previous session");

		loadSpecificProject(Constants.LAST_SESSION);
	}


	public void loadSpecificProject(final File file)
	{
		if (_project != null && file != null && file.isFile())
		{
			try
			{
				_project.loadConfigFile(file);
			}
			catch (final IllegalArgumentException e)
			{
				JOptionPane.showMessageDialog(_project, e.getMessage(), "Error while loading project", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			ApplicationLogger.getLogger().warning("Skip loading the previous session, because the file is missing");
		}
	}


	private void saveLatestProject()
	{
		ApplicationLogger.getLogger().info("Saving project");

		saveSpecificProject(Constants.LAST_SESSION);
	}

	public void saveSpecificProject(final File file)
	{
		if (_project != null && file != null)
		{
			_project.saveConfigFile(file);
		}
		else
		{
			ApplicationLogger.getLogger().info("Skip saving the project, because the information is missing");
		}
	}
}
