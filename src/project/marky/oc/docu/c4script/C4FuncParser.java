package project.marky.oc.docu.c4script;

import static project.marky.oc.docu.util.StringConstants.*;

import java.util.HashMap;

import project.marky.oc.docu.ApplicationLogger;

public class C4FuncParser
{
	public static final String VISIBILITY_PRIVATE = "private";
	public static final String VISIBILITY_PROTECTED = "protected";
	public static final String VISIBILITY_GLOBAL = "global";
	public static final String VISIBILITY_PUBLIC = "public";
	private String _text;
	private String _parameterText;

	private boolean _isParsed;
	private boolean _parsesParameters;

	private String _functionVisibility;
	private String _functionName;

	private final HashMap<String, C4TypeDef> _parameters;


	public C4FuncParser()
	{
		_text = EMPTY_STRING;
		_parameterText = EMPTY_STRING;
		_isParsed = false;
		_parsesParameters = false;

		_parameters = new HashMap<String, C4TypeDef>();
	}


	public void feed(final String line)
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
		else if (line.contains(IDENTIFIER_HEADER_START))
		{
			feed(line.replace(IDENTIFIER_HEADER_START, EMPTY_STRING));
		}
		else if (line.contains(IDENTIFIER_HEADER_CLOSE))
		{
			feed(line.replace(IDENTIFIER_HEADER_CLOSE, EMPTY_STRING));
		}
		else
		{
			parseFunction(line);
		}

		if (_isParsed)
		{
			if (!_parameterText.equals(EMPTY_STRING))
			{
				parseFunctionString();
				parseParameters();
			}
			else
			{
				parseFunctionString();
			}
		}
		else
		{
			ApplicationLogger.getLogger().info("... parsing " + _text);
		}
	}


	private void parseParameters()
	{
		final String[] pairsOfTypeAndParameter = _parameterText.split(",");

		for (int i = 0; i < pairsOfTypeAndParameter.length; i++)
		{
			final String[] split = pairsOfTypeAndParameter[i].split(SPACE_STRING);

			String type = null;
			String parameter = null;

			C4TypeDef c4type = C4TypeDef.C4V_Any;

			for (int j = 0; j < split.length; j++)
			{
				if (split[j].equals(EMPTY_STRING) || split[j].equals(SPACE_STRING))
				{
					continue;
				}

				if (type == null)
				{
					type = split[j];
					continue;
				}

				if (parameter == null)
				{
					parameter = split[j];
					break;
				}
			}

			if (type != null && parameter == null)
			{
				parameter = type;
				type = null;
			}

			if (type != null)
			{
				c4type = C4TypeDef.fromString(type);
			}

			_parameters.put(parameter, c4type);
		}
	}


	private void parseFunctionString()
	{
		final String[] split = _text.split(SPACE_STRING);

		for (final String entry : split)
		{
			if (entry.equals(EMPTY_STRING) || entry.equals("func")) continue;

			if (_functionVisibility == null && (entry.equals(VISIBILITY_GLOBAL) || entry.equals(VISIBILITY_PUBLIC) || entry.equals(VISIBILITY_PROTECTED) || entry.equals(VISIBILITY_PRIVATE)))
			{
				_functionVisibility = entry;
				continue;
			}

			if (_functionName == null)
			{
				_functionName = entry;
			}
		}

		if (_functionVisibility == null)
		{
			_functionVisibility = VISIBILITY_PUBLIC;
		}

		ApplicationLogger.getLogger().info(" * > parsed function: " + _functionVisibility + " " + _functionName + "()");
	}


	private void parseFunction(final String line)
	{
		if (_text.equals(EMPTY_STRING) && _parameterText.equals(EMPTY_STRING))
		{
			if (line.contains(IDENTIFIER_PAR_OPEN))
			{
				final String[] divide = line.split("\\" + IDENTIFIER_PAR_OPEN);

				switch (divide.length)
				{
					case 0:
						_parsesParameters = true;
						return;
					case 1:
						_text = divide[0];
						_parsesParameters = true;
						return;
					case 2:
					default:
						_text = divide[0];
						_parsesParameters = true;
						parseFunction(divide[1]);
						return;
				}
			}
		}

		if (!_parsesParameters && _text.equals(EMPTY_STRING))
		{
			_text += line;
		}

		if (_parsesParameters)
		{
			final int index = line.indexOf(IDENTIFIER_PAR_CLOSE);

			String text = line;
			if (index >= 0) text = line.substring(0, index);

			_parameterText += text;
		}

		if (line.contains(IDENTIFIER_PAR_CLOSE))
		{
			_isParsed = true;
		}
	}

	public boolean isFunctionEnd()
	{
		return _isParsed;
	}


	public String getFunctionName()
	{
		return _functionName;
	}


	public String getFunctionVisibility()
	{
		return _functionVisibility;
	}


	public HashMap<String, C4TypeDef> getFunctionParameters()
	{
		return _parameters;
	}

}
