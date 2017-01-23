package project.marky.oc.docu.logic;

import static project.marky.oc.docu.util.StringConstants.IDENTIFIER_PAR_CLOSE;
import static project.marky.oc.docu.util.StringConstants.IDENTIFIER_PAR_OPEN;
import static project.marky.oc.docu.util.StringConstants.SPACE_STRING;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import project.marky.oc.docu.DocuGenerator;
import project.marky.oc.docu.c4script.C4TypeDef;
import project.marky.oc.docu.html.StdHtmlFile;
import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.internal.interfaces.IParameter;


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

	// function specific stuff
	private String _returnsType;
	private final IParameter _returns;
	private final List<IParameter> _parameters;
	private final String _visibility;

	private final IDocuItem _docu;


	/**
	 * Constructor for a page in the documentation.
	 * 
	 * @param docu the page reads information from this parser.
	 */
	public DocuPage(final IDocuItem docu)
	{
		_identifier = docu.getTitle(); //docu.getIdentifier();
		_htmlID = docu.getTitle();

		_docu = docu;

		// function-specific stuff
		_returns = null;
		_parameters = null;
		_visibility = null;
	}


	/**
	 * Constructor for a function documentation.
	 * 
	 * @param docu the page reads information from this parser.
	 * @param function the page reads information from this parser.
	 */
	public DocuPage(final IFunction function)
	{
		_identifier = function.getTitle();

		_htmlID = function.getTitle();

		_docu = function;

		// function-specific stuff
		_returns = function.getReturnValue();
		_parameters = function.getParameters();
		_visibility = function.getAccessModifier();
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

		_html.write(SPACE_STRING + _docu.getTitle() + IDENTIFIER_PAR_OPEN);

		// write parameters into parenthesis
		final Iterator<IParameter> parameterIterator = _parameters.iterator();
		while (parameterIterator.hasNext())
		{
			final IParameter parameter = parameterIterator.next();

			//			final C4TypeDef type = parameter;

			/*if (type != null)*/ _html.span(typespan).write(parameter.getType().getString())._span();
			_html.write(SPACE_STRING + parameter.getName());

			if (parameterIterator.hasNext()) _html.write(", ");
		}

		_html.write(IDENTIFIER_PAR_CLOSE + ";")._div().newline();

		// --------------------------------------------------------------------
		//
		// parameter docu

		if (_parameters.size() > 0)
		{
			_html.h2().write("Parameters")._h2().newline();

			for (final IParameter parameter : _parameters)
			{

				final String docu = parameter.getDocu();
				final C4TypeDef type = parameter.getType();

				// parameter name
				_html.div(parn).write(parameter.getName());

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

		if (_returns != null && _returns.getDocu() != null)
		{
			_html.h2().write("Return Value")._h2().newline();
			_html.div(part).write(styleparse(_returns.getDocu(), filemanager, root_folder, own_location));
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

		_html.h1("id=\" + _htmlID + \"").write(_docu.getTitle())._h1();

		// stuff
		docuHeaderParagraph("Engine version", _docu.getEngine());
		docuHeaderParagraph("Project version", _docu.getVersion());
		docuHeaderParagraph("Author", _docu.getAuthor());
		docuHeaderParagraph("Credits", _docu.getCredits());

		docuBodyParagraph("Description", _docu.getDescription(), filemanager, root_folder, own_location);

		buildFunctionDescription(filemanager, root_folder, own_location);

		docuBodyParagraph("Example", _docu.getExample(), filemanager, root_folder, own_location);
		docuBodyParagraph("Note", _docu.getNote(), filemanager, root_folder, own_location);
		// TODOdocuBodyParagraph("See Also", _related, filemanager, root_folder, own_location);

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
		return content;
		//TODO was: return Styleparser.parse(content, filemanager, root_folder, own_location);
	}


	public String getIdentifier()
	{
		return _identifier;
	}
}
