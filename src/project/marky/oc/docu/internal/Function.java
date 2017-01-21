package project.marky.oc.docu.internal;

import java.util.ArrayList;
import java.util.List;

import project.marky.oc.docu.internal.interfaces.IFunction;

public class Function extends DocuItem implements IFunction
{
	private final List<String> _parameters = new ArrayList<String>();
	private String _returnValue = null;
	private String _accessModifier = null;

	////////////////////////////////////////////////////////////////
	//
	// from interface

	@Override
	public List<String> getParameters()
	{
		return _parameters;
	}

	@Override
	public String getReturnValue()
	{
		return _returnValue;
	}

	@Override
	public String getAccessModifier()
	{
		return _accessModifier;
	}

	/////////////////////////////////////////////////////////////////
	//
	// setters

	public void setReturnValue(final String returnValue)
	{
		_returnValue = returnValue;
	}

	public void setAccessModifier(final String accessModifier)
	{
		_accessModifier = accessModifier;
	}
}
