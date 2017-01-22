package project.marky.oc.docu.internal;

import project.marky.oc.docu.c4script.C4TypeDef;
import project.marky.oc.docu.internal.interfaces.IParameter;

public class Parameter implements IParameter
{
	private final C4TypeDef _type;
	private final String _docu;
	private final String _name;


	public Parameter(final String name, final String docu, final C4TypeDef type)
	{
		_name = name;
		_docu = docu;
		_type = type;
	}


	@Override
	public C4TypeDef getType()
	{
		return _type;
	}


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public String getDocu()
	{
		return _docu;
	}
}
