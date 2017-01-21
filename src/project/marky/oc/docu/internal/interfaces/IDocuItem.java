package project.marky.oc.docu.internal.interfaces;

import java.util.List;


/**
 * Interface for a documentation item.
 */
public interface IDocuItem
{
	/**
	 * The title, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getTitle();


	/**
	 * The author, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getAuthor();


	/**
	 * The credits, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getCredits();


	/**
	 * The version, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getVersion();


	/**
	 * The engine version, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getEngine();


	/**
	 * The note, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getNote();


	/**
	 * The example, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getExample();


	/**
	 * The category, as denoted by TODO.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getCategory();


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
