package vamTeam;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExecutionLogger {

	/**
	 * Method starts logging
	 * 
	 * @param path path to folder in which log should be stored
	 * @return logger started logger
	 */

	private Logger logger = Logger.getLogger(ExecutionLogger.class.getName());
	private FileHandler fileHandler;

	public static CodeSource codeSource;
	private FileInputStream fileInputStream;

	Properties properties = new Properties();
	private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

	public Logger logger_start() {

		try {
			// This block configure the logger with handler and formatter
			URL url = null;
			codeSource = getClass().getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				url = new URL(codeSource.getLocation(), "conf/reportgenerator.properties");
			}
			fileInputStream = new FileInputStream(url.getFile());
			properties.load(fileInputStream);
			String logPath = properties.getProperty("logFilePath");

			fileHandler = new FileHandler(logPath + "Generic.log");
			logger.addHandler(fileHandler);

			fileHandler.setFormatter(new SimpleFormatter() {
				public synchronized String format(LogRecord lr) {
					return String.format(format, new Date(lr.getMillis()), lr.getLevel().getLocalizedName(),
							lr.getMessage());
				}
			});

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logger;
	}
}
