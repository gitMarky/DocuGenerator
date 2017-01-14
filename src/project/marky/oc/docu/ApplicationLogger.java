package project.marky.oc.docu;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;

/**
 * Utility class for logging.
 */
public class ApplicationLogger
{
	private static final String DOCU_GENERATOR_LOG = "DocuGenerator.log";
	private static Logger _logger = null;
	
	
	private ApplicationLogger() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("This is a utility class, it should not be instantiated.");
	}


	public static Logger getLogger()
	{
		if (_logger == null)
		{
			_logger = Logger.getLogger(DOCU_GENERATOR_LOG);

			FileHandler fh = null;
			try
			{
				fh = new FileHandler(new File(DOCU_GENERATOR_LOG).getAbsolutePath());
			}
			catch (final SecurityException e)
			{
				throw new IllegalStateException(e);
			}
			catch (final IOException e)
			{
				throw new IllegalStateException(e);
			}
			fh.setLevel(Level.ALL);
			fh.setFormatter(new ApplicationLogFormatter());
			_logger.addHandler(fh);
			_logger.info("Message:");

		}

		return _logger;
	}

	private static class ApplicationLogFormatter extends Formatter
	{
		@SuppressWarnings("deprecation")
		@Override
		public String format(final LogRecord record)
		{
			if (record == null)
			{
				throw new IllegalArgumentException("Parameter 'record' must not be null.");
			}

			final StringBuilder format = new StringBuilder();
			format.append(new Date(record.getMillis()).toLocaleString());
			extend(format, 25);
			format.append(getShortClassName(record) + "." + record.getSourceMethodName() + "()");
			extend(format, 60);
			format.append(record.getLevel());
			extend(format, 70);
			format.append(record.getMessage());
			format.append("\n");
			return format.toString();
		}


		private String getShortClassName(final LogRecord record)
		{
			if (record == null)
			{
				throw new IllegalArgumentException("Parameter 'record' must not be null.");
			}

			final String name = record.getSourceClassName();
			return name.substring(name.lastIndexOf(".") + 1);
		}


		private void extend(final StringBuilder builder, final int size)
		{
			if (builder == null)
			{
				throw new IllegalArgumentException("Parameter 'builder' must not be null.");
			}

			while (builder.length() < size)
				builder.append(" ");
		}
	}
}
