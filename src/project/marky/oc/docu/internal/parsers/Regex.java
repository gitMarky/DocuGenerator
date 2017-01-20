package project.marky.oc.docu.internal.parsers;


/**
 * Some regular expressions.
 */
class Regex
{
	/**
	 * Regex that finds JavaDoc comments.
	 */
	// header: /\*\*([^\*]|\*(?!/))*\*/
	static final String REGEX_DOCU = "/\\*\\*([^\\*]|\\*(?!/))*\\*/";

	// function "prefix": ^(public|protected|private)*.*func\s+
	// function prefix + declaration: ^(public|protected|private)*.*func\s+(\w+)\(\)
	// function prefix + declaration and parameters: ^(public|protected|private)*.*func\s+(\w+)\(([\w\s,]+)*\)
	// function prefix + declaration and parameters: ^(public|protected|private)*[ ]*func\s+(\w+)\(([\w\s,]+)*\)
	/**
	 * Regex that finds function declarations, without the function body.
	 */
	//private static final String REGEX_FUNCTION = "^(public|protected|private)*.*func\\s+(\\w+)\\(([\\w\\s,]+)*\\)";
	//private static final String REGEX_FUNCTION = "(?m)^(public|protected|private)*\\s*func\\s+(\\w+)\\(([\\w\\s,]+)*\\)";
	static final String REGEX_FUNCTION = "(?m)^(public|protected|private)*[ ]*func\\s+(\\w+)\\(([\\w\\s,]+)*\\)";

	/**
	 * Regex that finds line breaks.
	 */
	static final String REGEX_ANY_LINEBREAK = "[\\r\\n]";

	static final String REGEX_DOCUMENTED_FUNCTIONS = REGEX_DOCU + REGEX_ANY_LINEBREAK + "*" + REGEX_FUNCTION;

	static final String REGEX_UNDOCUMENTED_FUNCTIONS = "(?m)^" + REGEX_ANY_LINEBREAK + REGEX_FUNCTION;
}