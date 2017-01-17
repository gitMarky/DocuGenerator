package project.marky.oc.docu.internal.parsers;

/**
 * Parses file content and returns a page, if possible.
 */
public class PageParser
{
	private final String _content;

	public PageParser(final String content)
	{
		if (content == null)
		{
			throw new IllegalArgumentException("Content must not be null!");
		}

		_content = content;
	}

	String getHeader()
	{
		return null;
	}
}
