package project.marky.oc.docu.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher
{
	public static List<String> getAllMatches(final String source, final String expression)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("Parameter 'source' must not be null.");
		}
		if (expression == null)
		{
			throw new IllegalArgumentException("Parameter 'expression' must not be null.");
		}

		final List<String> allMatches = new ArrayList<String>();
		final Matcher m = Pattern.compile(expression).matcher(source);
		while (m.find())
		{
			allMatches.add(m.group());
		}

		return allMatches;
	}
}
