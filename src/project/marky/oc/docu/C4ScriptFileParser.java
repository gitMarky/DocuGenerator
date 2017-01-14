package project.marky.oc.docu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import static project.marky.oc.docu.StringConstants.*; 

public class C4ScriptFileParser
{
	
	private enum ParseMode
	{
		INACTIVE,
		HEADER,
		JAVADOC,
		FUNC;
	}
	
	private ParseMode _mode;
	
	private File _origin;
	
	private static String _filename;
	
	private final ArrayList<C4DocuParser> _headerList;
	private final HashMap<C4DocuParser, C4FuncParser> _funcList;
	
	private C4DocuParser _parserJDoc;
	private C4FuncParser _parserFunctions;
	
	static C4ScriptFileParser parseFile(final File input)
	{
		_filename = input.getParentFile().getName();
		
		if (input.getName().equalsIgnoreCase("docu.c"))
		{
			int dummy = 0;
		}
		
		try
		{			
			@SuppressWarnings ("resource") BufferedReader reader = new BufferedReader(new FileReader(input));
			
			C4ScriptFileParser handler = new C4ScriptFileParser();
			
				Stream<String> lines = reader.lines();
				
				Iterator<String> iter= lines.iterator();
				
				while (iter.hasNext())
				{
					String line = iter.next();
					
					handler.feed(line); // this guy is hungry for code
				}
				
				handler.done();
				
				handler._origin = input.getParentFile();
				
			return handler;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	public C4ScriptFileParser()
	{
		_mode = ParseMode.INACTIVE;
		_parserJDoc = null;
		_parserFunctions = null;
		_headerList = new ArrayList<C4DocuParser>();
		_funcList = new HashMap<C4DocuParser, C4FuncParser>();
	}
	
	/**
	 * Feeds a line to the parse handler.
	 * 
	 * @param line the line that should be parsed.
	 */
	public void feed(String line)
	{
		switch (_mode)
		{
			case INACTIVE:
				tryMergeFunctionAndDoc();
				tryStartParsing(line);
				break;
			case JAVADOC:
				tryParsingJavaDoc(line);
				break;
			case FUNC:
				tryParsingFunction(line);
				break;
			default:
				break;
		}
	}

	private void tryMergeFunctionAndDoc()
	{
		if (_parserFunctions != null && _parserJDoc != null)
		{
			_funcList.put(_parserJDoc, _parserFunctions);

			_parserJDoc = null;
			_parserFunctions = null;
		}
	}
	
	public void done()
	{
		addHeader();
	}
	
	private void addHeader()
	{
		if (_parserJDoc != null)
		{
			_parserJDoc.completeHeader(_filename);
			_headerList.add(_parserJDoc);
			_parserJDoc = null;
		}
	}

	private void tryStartParsing(String line)
	{
		if (line.contains(IDENTIFIER_JDOC_START))
		{
			// docu and another docu? must be a header
			addHeader();
			
			_mode = ParseMode.JAVADOC;
			feed(line);
		}
		else if (line.contains(IDENTIFIER_FUNC_START) && _parserFunctions == null)
		{
			_mode = ParseMode.FUNC;
			feed(line);
		}
		else
		{
	 	    // docu and a new line? must be a header
			addHeader();
		}
	}
	

	private void tryParsingJavaDoc(String line)
	{
		if (_parserJDoc == null)
		{
			_parserJDoc = new C4DocuParser();
		}
		
		_parserJDoc.feed(line);
		
		if (line.contains(IDENTIFIER_JDOC_CLOSE))
		{
			_mode = ParseMode.INACTIVE;
			_parserJDoc.printText();
		}
	}

	private void tryParsingFunction(String line)
	{
		if (_parserFunctions == null)
		{
			_parserFunctions = new C4FuncParser();
		}

		_parserFunctions.feed(line);
		
		if (_parserFunctions.isFunctionEnd())
		{
			_mode = ParseMode.INACTIVE;
			
			// delete function parser, this should remove the error that the wrong documentation is displayed.
			if (_parserJDoc == null)
			{
				_parserFunctions = null;
			}
		}
	}
	
	public HashMap<C4DocuParser, C4FuncParser> getFunctionList()
	{
		return _funcList;
	}
	
	public ArrayList<C4DocuParser> getHeaderList()
	{
		return _headerList;
	}
	
	public File getOrigin()
	{
		return _origin;
	}
	
}
