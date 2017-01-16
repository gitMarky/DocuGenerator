package project.marky.oc.docu;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.marky.oc.docu.gui.ProjectPanel;
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
		mainPanel.add(_project);
	}


	private void loadLatestProject()
	{
		ApplicationLogger.getLogger().info("Loading project from previous session");

		if (_project != null && Constants.LAST_SESSION.isFile())
		{
			_project.loadConfigFile(Constants.LAST_SESSION);
		}
		else
		{
			ApplicationLogger.getLogger().warning("Skip loading the previous session, because the file is missing");
		}
	}


	private void saveLatestProject()
	{
		ApplicationLogger.getLogger().info("Saving project");

		if (_project != null)
		{
			_project.saveConfigFile(Constants.LAST_SESSION);
		}
		else
		{
			ApplicationLogger.getLogger().info("Skip saving the project, because the information is missing");
		}
	}
}
