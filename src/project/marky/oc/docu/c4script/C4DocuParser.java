package project.marky.oc.docu.c4script;

import static project.marky.oc.docu.util.StringConstants.*;

import java.util.HashMap;

import project.marky.oc.docu.util.Helper;

public class C4DocuParser
{
	private HashMap<String, String> _parameterMap;
	
	protected enum JDocTag
	{
		AUTHOR(DOCU_TAG_AUTHOR, false),
		VERSION(DOCU_TAG_VERSION, false),
		NOTE(DOCU_TAG_NOTE, false),
		EXAMPLE(DOCU_TAG_EXAMPLE, false),

		TITLE(DOCU_TAG_TITLE, false),
		FILE(DOCU_TAG_IDENTIFIER, false),
		IGNORE(DOCU_TAG_IGNORE, false),
		LINK(DOCU_TAG_RELATED, false),
		ENGINE(DOCU_TAG_ENGINE, false),
		CATEGORY(DOCU_TAG_CATEGORY, false),
		CREDITS(DOCU_TAG_CREDITS, false),

		PARAM(DOCU_TAG_PARAM, true),
		RETURNS(DOCU_TAG_RETURNS, false);
		//FLINK(DOCU_TAG_LINK, false);
		
		private final String _tag;
		private final boolean _needsIdentifier;
		
		JDocTag(final String tag, final boolean needsIdentifier)
		{
			_tag = tag;
			_needsIdentifier = needsIdentifier;
		}
		
		public String getTag()
		{
			return _tag;
		}
		
		public boolean needsIdentifier()
		{
			return _needsIdentifier;
		}
		
		public static JDocTag fromString(String from)
		{
			for (JDocTag value : values())
			{
				if (from.equals(value.getTag()))
				{
					return value;
				}
			}
			
			return null;
		}
	}
	
	/** The current tag while parsing. */
	private JDocTag _currentTag;
	
	/** The current parameter name while parsing. */
	private String _currentIdentifier;
	
	/** The text that is put into documentation. */
	private String _currentDocu;
	
	/** General docu for the function. */
	private String _functionDocu;
	
	private C4TypeDef _returnsType;
	private String _returnsDocu; 
	private String _authorDocu;
	private String _versionDocu;
	private String _engineDocu;
	private String _noteDocu; 
	private String _linkDocu; 
	private String _exampleDocu; 
	private String _categoryDocu;
	private boolean _ignoreThis;
	private String _title;
	private String _identifier;
	private String _credits;
	
	
	public C4DocuParser()
	{
		_functionDocu = EMPTY_STRING;
		_parameterMap = new HashMap<String, String>();
		_currentTag = null;
		_currentIdentifier = null;
		_currentDocu = null;
		_ignoreThis = false;
	}
	
	public void feed(String line)
	{
		if (line == null) return;
		
		
		if (line.startsWith(SPACE_STRING))
		{
			feed(line.replaceFirst(SPACE_STRING, EMPTY_STRING));
		}
		else if (line.startsWith(TAB_STRING))
		{
			feed(line.replace(TAB_STRING, EMPTY_STRING));
		}
		else if (line.contains(IDENTIFIER_JDOC_START))
		{
			feed(line.replace(IDENTIFIER_JDOC_START, EMPTY_STRING));
		}
		else if (line.contains(IDENTIFIER_JDOC_CLOSE))
		{
			feed(line.replace(IDENTIFIER_JDOC_CLOSE, EMPTY_STRING));
			finishParsingCurrentTag();
		}
		else if (line.startsWith(ASTERISK))
		{
			feed(line.replace(ASTERISK, EMPTY_STRING));
		}
		else if (!line.isEmpty())
		{
			parseLine(line);
		}
	}

	private void parseLine(String line)
	{
		for (JDocTag tag : JDocTag.values())
		{
			if (line.contains(tag.getTag()))
			{
				finishParsingCurrentTag();
				startParsingNewTag(line);
				return; //break;
			}
		}
		
		continueParsingCurrentTag(line);
	}

	private void startParsingNewTag(String line)
	{
		_currentTag = null;
		_currentIdentifier = null;
		_currentDocu = EMPTY_STRING;
		
		String[] words = line.split(SPACE_STRING);
		
		for (String word : words)
		{
			if (word.isEmpty()) continue;
			
			if (_currentTag == null)
			{
				_currentTag = JDocTag.fromString(word);
				continue;
			}
			
			if (_currentTag == null)
			{
				continue;
			}
			
			if (_currentIdentifier == null && _currentTag.needsIdentifier())
			{
				_currentIdentifier = word;
				continue;
			}
			else
			{
				if (!_currentDocu.isEmpty()) _currentDocu += SPACE_STRING;
				
				_currentDocu += word;
			}
		}
		
		_currentDocu += NEWLINE_STRING;
	}

	private void continueParsingCurrentTag(String line)
	{
		if (_currentTag != null)
		{
			_currentDocu += line + NEWLINE_STRING;
		}
		else
		{
			_functionDocu += line + NEWLINE_STRING;
		}
	}

	private void finishParsingCurrentTag()
	{
		
		if (_currentTag == null)
		{
			//ApplicationLogger.getLogger().info(">>> Cannot finish parsing 'null': " + _functionDocu);
			return;
		}
		
		_currentDocu +=  NEWLINE_STRING;
		
		switch (_currentTag)
		{
			case PARAM:
				_parameterMap.put(_currentIdentifier, _currentDocu);
				break;
			case AUTHOR:
				_authorDocu = _currentDocu;
				break;
			case ENGINE:
				_engineDocu = _currentDocu;
				break;
			case EXAMPLE:
				_exampleDocu = _currentDocu;
				break;
			case NOTE:
				_noteDocu = _currentDocu;
				break;
			case LINK:
				_linkDocu = _currentDocu;
				break;
			case RETURNS:

				String[] help = Helper.splitIdentifierAndDocu(_currentDocu);
				
				final String type = help[0];
				final String docu = help[1];
				
				if (!type.equals(C4TypeDef.C4V_Any.getString())
				 && C4TypeDef.fromString(type) == C4TypeDef.C4V_Any)
				{
					_returnsType = C4TypeDef.C4V_Any;
					_returnsDocu = _currentDocu;
				}
				else
				{
					_returnsType = C4TypeDef.fromString(type);
					_returnsDocu = docu;
				}
				break;
			case VERSION:
				_versionDocu = _currentDocu;
				break;
			case CATEGORY:
				_categoryDocu = _currentDocu;
				break;
			case IGNORE:
				_ignoreThis = true;
				break;
			case TITLE:
				_title = _currentDocu;
				break;
			case FILE:
				_identifier = _currentDocu.replace("\n", "");
				break;
			case CREDITS:
				_credits = _currentDocu;
				break;
			default:
				return;
		}
	}

	public void printText()
	{
//		ApplicationLogger.getLogger().info("Function docu:");
//		ApplicationLogger.getLogger().info("" + _functionDocu);
//		
//		ApplicationLogger.getLogger().info("Parameters + Docu:");
//		
//		Iterator<String> iter = _parameterMap.keySet().iterator();
//		
//		while (iter.hasNext())
//		{
//			String name = iter.next();
//			
//			String docu = _parameterMap.get(name);
//			
//			ApplicationLogger.getLogger().info("" + name + " : " + docu);
//		}
	}

	public HashMap<String, String> getParameterMap()
	{
	
		return _parameterMap;
	}

	public String getDescriptionDocu()
	{
		return _functionDocu;
	}

	public String getReturnsDocu()
	{
		return _returnsDocu;
	}
	
	public C4TypeDef getReturnsType()
	{
		return _returnsType;
	}

	public String getAuthorDocu()
	{
		return _authorDocu;
	}

	public String getVersionDocu()
	{
		return _versionDocu;
	}

	public String getEngineDocu()
	{
		return _engineDocu;
	}

	public String getNoteDocu()
	{
		return _noteDocu;
	}

	public String getRelatedDocu()
	{
		return _linkDocu;
	}

	public String getExampleDocu()
	{
		return _exampleDocu;
	}

	public String getCategoryDocu()
	{
		return _categoryDocu;
	}
	
	public boolean ignoreThis()
	{
		return _ignoreThis;
	}

	public String getIdentifier()
	{
		return _identifier;
	}

	public String getCredits()
	{
		return _credits;
	}


	public String getTitle()
	{
		return _title;
	}

	public void completeHeader(String filename)
	{
		if (_identifier == null)
		{
			_identifier = "index";
		}
		
		if (_title == null)
		{
			_title = createTitle(filename);
		}
	}

	private String createTitle(String filename)
	{
		StringBuffer title = new StringBuffer("");

		for (int start = 0, end = 0; end < filename.length(); end++)
		{
			boolean addWord = false;
			boolean skipExtension = false;
			
			if (Character.isUpperCase(filename.charAt(end)) && start != end)
			{
				addWord = true;
			}
			else if (filename.charAt(end) == '.')
			{
				addWord = true;
				skipExtension = true;
			}
			else if (end >= filename.length() - 1)
			{
				addWord = true;
			}
			
			if (addWord)
			{
				if (title.length() > 0)
				{
					title.append(" ");
				}
				
				final String word = filename.substring(start, end);
				title.append(word);
				start = end;
			}
			
			if (skipExtension)
			{
				break;
			}
		}
		
		return title.toString();
	}
}
