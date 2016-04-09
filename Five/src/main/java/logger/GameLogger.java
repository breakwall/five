package logger;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GameLogger {
	private static GameLogger instance = new GameLogger();
	private Logger logger = Logger.getLogger("game logger");
	private GameLogger() {
		try {
			logger.setLevel(Level.INFO);
			FileHandler handler = new FileHandler("moves_%g.txt", 0, 10, false);
			handler.setLevel(Level.FINE);
			handler.setFormatter(new LogFormatter());
			logger.addHandler(handler);

//			FileHandler handler2 = new FileHandler("values_%g.txt", 0, 2, false);
//			handler2.setLevel(Level.FINER);
//			handler2.setFormatter(new LogFormatter());
//			handler2.setFilter(new Filter() {
//
//				@Override
//				public boolean isLoggable(LogRecord record) {
//					return record.getLevel() == Level.FINER;
//				}
//			});
//			logger.addHandler(handler2);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static GameLogger getInstance() {
		return instance;
	}

	public void logInfo(String message) {
		logger.log(Level.INFO, message);
	}

	public void logDebug(String message) {
		logger.log(Level.FINE, message);
	}

	public void logFiner(String message) {
		logger.log(Level.FINER, message);
	}

	class LogFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			StringBuffer sb = new StringBuffer(500);
			sb.append(record.getLevel()).append(", ");
			appendISO8601(sb, record.getMillis());
			sb.append(", ");
			sb.append(record.getMessage()).append("\n");
			return sb.toString();
		}
	}

    // Append the time and date in ISO 8601 format
	private void appendISO8601(StringBuffer sb, long millis) {
		Date date = new Date(millis);
//		sb.append(date.getYear() + 1900);
//		sb.append('-');
//		a2(sb, date.getMonth() + 1);
//		sb.append('-');
//		a2(sb, date.getDate());
//		sb.append('T');
		a2(sb, date.getHours());
		sb.append(':');
		a2(sb, date.getMinutes());
		sb.append(':');
		a2(sb, date.getSeconds());
	}

	// Append a two digit number.
	private void a2(StringBuffer sb, int x) {
		if (x < 10) {
			sb.append('0');
		}
		sb.append(x);
	}
}
