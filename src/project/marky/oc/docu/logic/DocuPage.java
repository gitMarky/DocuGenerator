package project.marky.oc.docu.logic;

import static project.marky.oc.docu.util.StringConstants.IDENTIFIER_PAR_CLOSE;
import static project.marky.oc.docu.util.StringConstants.IDENTIFIER_PAR_OPEN;
import static project.marky.oc.docu.util.StringConstants.SPACE_STRING;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.c4script.C4DocuParser;
import project.marky.oc.docu.c4script.C4FuncParser;
import project.marky.oc.docu.c4script.C4TypeDef;
import project.marky.oc.docu.html.StdHtmlFile;
import project.marky.oc.docu.html.Styleparser;


/**
 * <p>
 * Represents a documentation page.
 * </p>
 */
public class DocuPage
{
	// for namespace and doculink
	private final String _identifier;

	private StdHtmlFile _html;

	// identification
	@SuppressWarnings("unused")
	private final String _htmlID;
	private final String _title;

	// contents
	private final String _engine;
	private final String _version;
	private final String _author;
	private final String _credits;
	private final String _description;

	private final String _example;
	private final String _note;
	private final String _related;

	// function specific stuff
	private String _returnsType;
	private final String _returns;
	private final HashMap<String, String> _parameters;
	private final HashMap<String, C4TypeDef> _fparam;
	private final String _visibility;


	/**
	 * Constructor for a page in the documentation.
	 * 
	 * @param docu the page reads information from this parser.
	 */
	public DocuPage(final C4DocuParser docu)
	{
		_identifier = docu.getIdentifier();

		_htmlID = docu.getIdentifier();
		_title = docu.getTitle();

		_engine = docu.getEngineDocu();
		_version = docu.getVersionDocu();
		_author = docu.getAuthorDocu();
		_credits = docu.getCredits();

		_description = docu.getDescriptionDocu();

		_example = docu.getExampleDocu();
		_note = docu.getNoteDocu();
		_related = docu.getRelatedDocu();

		// function-specific stuff
		_returns = null;
		_parameters = null;
		_visibility = null;
		_fparam = null;
	}


	/**
	 * Constructor for a function documentation.
	 * 
	 * @param docu the page reads information from this parser.
	 * @param function the page reads information from this parser.
	 */
	public DocuPage(final C4DocuParser docu, final C4FuncParser function)
	{
		_identifier = function.getFunctionName();

		_htmlID = function.getFunctionName();
		_title = function.getFunctionName();

		_engine = docu.getEngineDocu();
		_version = docu.getVersionDocu();
		_author = docu.getAuthorDocu();
		_credits = docu.getCredits();

		_description = docu.getDescriptionDocu();

		_example = docu.getExampleDocu();
		_note = docu.getNoteDocu();
		_related = docu.getRelatedDocu();

		// function-specific stuff
		_returns = docu.getReturnsDocu();
		if (docu.getReturnsType() != null)
		{
			_returnsType = docu.getReturnsType().getString();
		}
		_parameters = docu.getParameterMap();
		_visibility = function.getFunctionVisibility();
		_fparam = function.getFunctionParameters();
	}


	/**
	 * Builds the description for a function.
	 * 
	 * @param filemanager used for reading certain files. Seems to be unnecesary. TODO
	 * @param root_folder the root folder of the project? Seems to be a bad implementation.
	 * @param own_location the own file location? Seems to be a bad implementation.
	 */
	private void buildFunctionDescription(final DocuGenerator filemanager, final File root_folder, final File own_location)
	{
		if (_returns == null && _parameters == null && _visibility == null) return;

		// --------------------------------------------------------------------
		//
		// function with parameters listed

		final String typespan = "class=\"type\"";
		final String fnxsyntax = "class=\"fnsyntax\"";
		final String parn = "class=\"parn\"";
		final String part = "class=\"part\"";

		_html.h2().write("Syntax")._h2().newline();

		_html.div(fnxsyntax).span(typespan);

		if (_visibility != null)
		{
			_html.write(_visibility + SPACE_STRING);
		}

		if (_returnsType != null)
		{
			_html.write(_returnsType);
		}
		else
		{
			_html.write("" + C4TypeDef.C4V_Any.getString());
		}
		_html._span();

		_html.write(SPACE_STRING + _title + IDENTIFIER_PAR_OPEN);

		// write parameters into parenthesis
		final Iterator<String> parameterIterator = _fparam.keySet().iterator();
		while (parameterIterator.hasNext())
		{
			final String parameter = parameterIterator.next();

			final C4TypeDef type = _fparam.get(parameter);

			if (type != null) _html.span(typespan).write(type.getString())._span();
			_html.write(SPACE_STRING + parameter);

			if (parameterIterator.hasNext()) _html.write(", ");
		}

		_html.write(IDENTIFIER_PAR_CLOSE + ";")._div().newline();

		// --------------------------------------------------------------------
		//
		// parameter docu

		final Iterator<String> docuIterator = _parameters.keySet().iterator();

		if (docuIterator.hasNext())
		{
			_html.h2().write("Parameters")._h2().newline();

			while (docuIterator.hasNext())
			{
				final String parameter = docuIterator.next();

				final String docu = _parameters.get(parameter);
				final C4TypeDef type = _fparam.get(parameter);

				// parameter name
				_html.div(parn).write(parameter);

				if (type != null)
				{
					_html.write(", ");
					_html.span(typespan).write(type.getString())._span();
				}

				_html.write(": ")._div().newline();

				// parameter description
				_html.div(part).write(styleparse(docu, filemanager, root_folder, own_location))._div().newline();
			}
		}

		// --------------------------------------------------------------------
		//
		// return value

		if (_returns != null)
		{
			_html.h2().write("Return Value")._h2().newline();
			_html.div(part).write(styleparse(_returns, filemanager, root_folder, own_location));
			_html._div().newline();
		}
	}


	/**
	 * Main method for making a html file from the docu page.
	 * 
	 * @param filemanager TODO
	 * @param root_folder TODO
	 * @param own_location TODO
	 * @param css_location TODO
	 */
	public void convertToHtml(final DocuGenerator filemanager, final File root_folder, final File own_location, final String css_location)
	{
		if (_html == null)
		{
			_html = new StdHtmlFile();
		}

		_html.html().body();

		// stylesheet

		_html.head().link("rel=\"stylesheet\" type=\"text/css\" href=\"" + css_location + "\"").title().write(_identifier)._title()._head();
		// heading

		_html.h1("id=\" + _htmlID + \"").write(_title)._h1();

		// stuff
		docuHeaderParagraph("Engine version", _engine);
		docuHeaderParagraph("Project version", _version);
		docuHeaderParagraph("Author", _author);
		docuHeaderParagraph("Credits", _credits);

		docuBodyParagraph("Description", _description, filemanager, root_folder, own_location);

		buildFunctionDescription(filemanager, root_folder, own_location);

		docuBodyParagraph("Example", _example, filemanager, root_folder, own_location);
		docuBodyParagraph("Note", _note, filemanager, root_folder, own_location);
		docuBodyParagraph("See Also", _related, filemanager, root_folder, own_location);

		_html._body()._html();
	}


	private void docuHeaderParagraph(final String description, final String item)
	{
		if (item != null) _html.p().b().write(description + ": ")._b().write(item)._p().newline();
	}


	private void docuBodyParagraph(final String description, final String content, final DocuGenerator filemanager, final File root_folder, final File own_location)
	{
		if (content != null)
		{
			_html.h2().write(description)._h2().newline().write(styleparse(content, filemanager, root_folder, own_location)).newline();
		}
	}


	public void saveHtmlFile(final File outputFolder)
	{
		_html.saveToFile(outputFolder);
	}


	private String styleparse(final String content, final DocuGenerator filemanager, final File root_folder, final File own_location)
	{
		return Styleparser.parse(content, filemanager, root_folder, own_location);
	}


	public String getIdentifier()
	{
		return _identifier;
	}
}
