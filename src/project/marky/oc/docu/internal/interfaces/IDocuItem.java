package project.marky.oc.docu.internal.interfaces;

/**
 * Interface for a documentation item.
 */
public interface IDocuItem
{
	/**
	 * A identifier for the item.
	 * 
	 * @return a directory-unique string
	 */
	String getIdentifier();

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
	 * The description, as in the main body of the documentation.
	 * 
	 * @return {@code null} if the value is not defined.
	 */
	String getDescription();
}
