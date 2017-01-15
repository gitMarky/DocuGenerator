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

				new DocuGui();

				//				ApplicationLogger.getLogger().info(Constants.HLINE);
				//				ApplicationLogger.getLogger().info("Setup");
				//				ApplicationLogger.getLogger().info(Constants.HLINE);
				//
				//GUI.getMenuSelectionPanel().onCreatureSelected();

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
	// Internal classes

	private final class GuiUpdateThread implements Runnable
	{
		private long lastUpdate = 0;
		private final long updateInterval = 500;
		private final DocuGui target;

		private GuiUpdateThread(final DocuGui target)
		{
			this.target = target;
		}


		@Override
		public void run()
		{
			while (target != null)
			{
				if (System.currentTimeMillis() > (lastUpdate + updateInterval))
				{
					doUpdate();
				}
			}
		}

		private void doUpdate()
		{
			lastUpdate = System.currentTimeMillis();

			if (target != null)
			{
				target.updateView();
			}
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////7
	//
	// Actual class


	private final JFrame _frame = new JFrame();
	private final GuiUpdateThread _guiUpdate;


	private DocuGui()
	{
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);

		_frame.add(panel);
		_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		assembleGui(panel);

		_guiUpdate = new GuiUpdateThread(this);
		new Thread(_guiUpdate).start();
	}


	private void assembleGui(final JPanel mainPanel)
	{
		mainPanel.add(new ProjectPanel());
	}


	private void updateView()
	{
		ApplicationLogger.getLogger().info("Update view");
		_frame.pack();
		_frame.setVisible(true);
	}
}
