package project.marky.oc.docu.internal.interfaces;

import java.util.List;

public interface IFunction extends IDocuItem
{
	/**
	 * The parameters, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	List<String> getParameters();


	/**
	 * The return value, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getReturnValue();
}
