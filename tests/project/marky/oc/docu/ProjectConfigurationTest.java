package project.marky.oc.docu;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import project.marky.oc.docu.internal.ProjectConfiguration;
import project.marky.oc.docu.internal.interfaces.IProjectConfiguration;

public class ProjectConfigurationTest
{

	@Test
	public void testLoadFromXml()
	{
		final File root = new File("tests\\project\\marky\\oc\\docu\\resources");
		final IProjectConfiguration testObject = ProjectConfiguration.loadFromXml(new File(root, "project_test.xml"));

		assertEquals("Title of test project", testObject.getTitle());
		assertEquals(new File(root, "dir1"), testObject.getSource());
		assertEquals(new File(root, "dir2"), testObject.getOutput());
		assertEquals(new File(root, "dir3\\style.css"), testObject.getStylesheet());
	}

}
