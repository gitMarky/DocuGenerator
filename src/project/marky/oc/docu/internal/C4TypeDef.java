package project.marky.oc.docu.internal;

public enum C4TypeDef
{
	C4V_Any("any"),
	C4V_Bool("bool"),
	C4V_Def("id"),
	C4V_C4Object("object"),
	C4V_Int("int"),
	C4V_Nil("nil"),
	C4V_PropList("proplist"),
	C4V_Array("array"),
	C4V_String("string");

	private final String _type;

	C4TypeDef(final String type)
	{
		_type = type;
	}

	public String getString()
	{
		return _type;
	}

	public static C4TypeDef fromString(final String string)
	{
		for (final C4TypeDef def : values())
		{
			if (def.getString().equals(string))
			{
				return def;
			}
		}

		return C4V_Any;
	}

}
