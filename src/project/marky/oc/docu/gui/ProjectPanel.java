package project.marky.oc.docu.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * Panel that holds the project settings and generate-button.
 */
@SuppressWarnings("serial") // no serialization intended
public class ProjectPanel extends JPanel
{
	public ProjectPanel()
	{
		super();
		this.setBorder(BorderFactory.createTitledBorder("Project"));
	}
}
