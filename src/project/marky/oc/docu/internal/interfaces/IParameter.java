package project.marky.oc.docu.internal.interfaces;

import project.marky.oc.docu.internal.C4TypeDef;

public interface IParameter
{
	/**
	 * Gets the type of the parameter.
	 * 
	 * @return the type.
	 */
	C4TypeDef getType();


	/**
	 * Gets the name of the parameter.
	 * 
	 * @return the name.
	 */
	String getName();


	/**
	 * Gets the documentation for that parameter.
	 * 
	 * @return the documentation.
	 */
	String getDocu();
}
