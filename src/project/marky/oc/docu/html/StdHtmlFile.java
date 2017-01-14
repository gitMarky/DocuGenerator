package project.marky.oc.docu.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import project.marky.oc.docu.util.Helper;
import project.marky.oc.docu.util.StringConstants;

public class StdHtmlFile
{
	String _content;
	
	File _location;
	
	private static final String HTML_TAG_HTML = "html";
	private static final String HTML_TAG_BODY = "body";
	private static final String HTML_TAG_H1 = "h1";
	private static final String HTML_TAG_H2 = "h2";
	private static final String HTML_TAG_P = "p";
	private static final String HTML_TAG_B = "b";
	private static final String HTML_TAG_DIV = "div";
	private static final String HTML_TAG_SPAN = "span";
	private static final String HTML_TAG_HEAD = "head";
	private static final String HTML_TAG_TITLE = "title";
	
	private int _indent = 0;
	
	public StdHtmlFile()
	{
		_content = "";
	}
	
	public class StdHtmlTag
	{
		final String _type;
		String _attributes;
		
		public StdHtmlTag(final String type)
		{
			_type = type;
			_attributes = null;
		}
		
		public StdHtmlTag(final String type, final String attr)
		{
			_type = type;
			_attributes = attr;
		}
		
		public String open()
		{
			String value = "<" + _type;
			
			if (_attributes != null)
			{
				value += " " + _attributes.toString();
			}
			
			value += ">";
			
			return value;
		}
		
		public String close()
		{
			return "</" + _type + ">";
		}
	}
	
	public class StdHtmlAttributes
	{
		final HashMap<String, String> _attributes = new HashMap<String, String>();
		
		public StdHtmlAttributes()
		{
			
		}
	}
	
	private void openTag(final String type)
	{
		_content += new StdHtmlTag(type).open();
	}
	
	private void openTag(final String type, final String attributes)
	{
		_content += new StdHtmlTag(type, attributes).open();
	}
	
	private void closeTag(final String type)
	{
		_content += new StdHtmlTag(type).close();
	}
	
	public StdHtmlFile write(final String text)
	{
		_content += text;
		return this;
	}

	public StdHtmlFile html()
	{
		openTag(HTML_TAG_HTML);
		indent(+1);
		newline();
		return this;
	}
	
	public StdHtmlFile _html()
	{
		closeTag(HTML_TAG_HTML);
		indent(-1);
		newline();
		return this;
	}
	


	private void indent(int i)
	{
		_indent = Math.max(0, _indent + i);
	}

	public StdHtmlFile body()
	{
		openTag(HTML_TAG_BODY);
		indent(+1);
		newline();
		return this;
	}
	
	public StdHtmlFile _body()
	{
		closeTag(HTML_TAG_BODY);
		indent(-1);
		newline();
		return this;
	}

	public StdHtmlFile h1(String string)
	{
		openTag(HTML_TAG_H1, string);
		return this;
	}
	
	public StdHtmlFile _h1()
	{
		closeTag(HTML_TAG_H1);
		return this;
	}

	public StdHtmlFile h2()
	{
		openTag(HTML_TAG_H2);
		return this;
	}
	
	public StdHtmlFile _h2()
	{
		closeTag(HTML_TAG_H2);
		return this;
	}
	
	public StdHtmlFile newline()
	{
		_content += "\n";
		doIndent();
		return this;
	}

	private void doIndent()
	{
		for (int i = _indent; i > 0; i--)
		{
			_content += "    ";
		}
	}

	public StdHtmlFile p()
	{
		openTag(HTML_TAG_P);
		return this;
	}

	public StdHtmlFile _p()
	{
		closeTag(HTML_TAG_P);
		return this;
	}
	
	public StdHtmlFile b()
	{
		openTag(HTML_TAG_B);
		return this;
	}

	public StdHtmlFile _b()
	{
		closeTag(HTML_TAG_B);
		return this;
	}


	public StdHtmlFile div(String string)
	{
		openTag(HTML_TAG_DIV, string);
		return this;
	}

	public StdHtmlFile _div()
	{
		closeTag(HTML_TAG_DIV);
		return this;
	}


	public StdHtmlFile span(String string)
	{
		openTag(HTML_TAG_SPAN, string);
		return this;
	}

	public StdHtmlFile _span()
	{
		closeTag(HTML_TAG_SPAN);
		return this;
	}
	
	public StdHtmlFile head()
	{
		openTag(HTML_TAG_HEAD);
		return this;
	}

	public StdHtmlFile _head()
	{
		closeTag(HTML_TAG_HEAD);
		return this;
	}
	
	public StdHtmlFile title()
	{
		openTag(HTML_TAG_TITLE);
		return this;
	}

	public StdHtmlFile _title()
	{
		closeTag(HTML_TAG_TITLE);
		return this;
	}
	
	public StdHtmlFile link(String attributes)
	{
		_content += "<link " + attributes + "/>";
		return this;
	}

	public void saveToFile(File outputFile)
	{
		Helper.ensureCreateFile(outputFile);
		
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(outputFile);
			writer.write(_content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (writer != null)
				{
					writer.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}

	protected void writeLine(final String line)
	{
		write(line + StringConstants.NEWLINE_STRING);
	}
}
