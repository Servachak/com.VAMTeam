package com.VAMTeam;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExecutionLoger {

	/**
	 * Method starts logging
	 * 
	 * @param path path to folder in which log should be stored
	 * @return logger started logger
	 */
	DateFormat dateFormat;
	Date date;
	Logger logger;
	FileHandler fh;

	public Logger logger_start() {

		dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_");
		date = new Date();
		logger = Logger.getLogger("MyLog");

		try {
			CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
			URL path = null;
			if (codeSource != null) {
				path = new URL(codeSource.getLocation(), "log/");
			}
			// This block configure the logger with handler and formatter
			fh = new FileHandler(path + "LOGS" + File.separator + dateFormat.format(date) + "reportGenerator.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logger;
	}
}
