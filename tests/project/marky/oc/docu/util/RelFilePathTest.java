package project.marky.oc.docu.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class RelFilePathTest
{
	private File a = new File("C:\\Test\\Bla\\Bli\\Blu\\");


	@Test
	public void test1()
	{
		File b = new File(a, "folder1\\file.extension");
		File c = new File(a, "folder2\\file.extension");

		assertEquals("..\\folder2\\file.extension", RelFilePath.fromTo(b, c));
	}

	
	@Test
	public void test2()
	{
		File b = new File(a, "folder1\\folder2\\folder3\\file.extension");
		File c = new File(a, "folder2\\file.extension");
		
		assertEquals("..\\..\\..\\folder2\\file.extension", RelFilePath.fromTo(b, c));
	}


	@Test
	public void test3()
	{
		File b = new File(a, "folder1\\folder2\\folder3\\file.extension");
		File c = new File("E:\\Test");

		assertEquals("E:\\Test", RelFilePath.fromTo(b, c));
	}


	@Test
	public void test4()
	{
		File b = new File(a, "folder1\\folder2\\testfile.extension");
		File c = new File(a, "folder1\\folder2\\folder3\\file.extension");

		assertEquals("folder3\\file.extension", RelFilePath.fromTo(b, c));
	}


	@Test
	public void test5()
	{
		File workspace = new File("C:\\Editing\\Clonk\\eclipseworkspace");
		File project = new File(workspace, "RPGLibrary\\RPGLibrary.c4d");
		File script = new File(project, "Libraries L.c4d\\Functionalities F.c4d\\DayNightCycle _C.c4d\\Script.c");

		assertEquals("Libraries L.c4d\\Functionalities F.c4d\\DayNightCycle _C.c4d\\Script.c", RelFilePath.fromTo(project, script));
	}
}
