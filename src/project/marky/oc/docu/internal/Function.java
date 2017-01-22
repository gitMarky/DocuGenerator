package project.marky.oc.docu.internal;

import java.util.ArrayList;
import java.util.List;

import project.marky.oc.docu.internal.interfaces.IDocuItem;
import project.marky.oc.docu.internal.interfaces.IFunction;
import project.marky.oc.docu.internal.interfaces.IParameter;

public class Function extends DocuItem implements IFunction
{
	private final List<IParameter> _parameters = new ArrayList<IParameter>();
	private IParameter _returnValue = null;
	private String _accessModifier = null;

	////////////////////////////////////////////////////////////////
	//
	// from interface

	public Function(final IDocuItem docu)
	{
		setAuthor(docu.getAuthor());
		setCategory(docu.getCategory());
		setCredits(docu.getCredits());
		setDescription(docu.getDescription());
		setEngine(docu.getEngine());
		setExample(docu.getExample());
		setNote(docu.getNote());
		setTitle(docu.getTitle());
		setVersion(docu.getVersion());
	}

	public Function()
	{
	}

	@Override
	public List<IParameter> getParameters()
	{
		return _parameters;
	}

	@Override
	public IParameter getReturnValue()
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

	public void setReturnValue(final IParameter returnValue)
	{
		_returnValue = returnValue;
	}

	public void setAccessModifier(final String accessModifier)
	{
		_accessModifier = accessModifier;
	}
}
