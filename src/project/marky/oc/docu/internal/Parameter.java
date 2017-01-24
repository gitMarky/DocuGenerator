package project.marky.oc.docu.internal;

import project.marky.oc.docu.internal.interfaces.IParameter;

public class Parameter implements IParameter
{
	private final C4TypeDef _type;
	private String _docu;
	private final String _name;


	public Parameter(final String name, final String docu, final C4TypeDef type)
	{
		_name = name;
		_docu = docu;
		_type = type;
	}


	public void setDocu(final String docu)
	{
		_docu = docu;
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
