package com.rgenerator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Logger;
import com.VAMTeam.ExecutionLogger;

public class MoveFile {

	public static CodeSource codeSource;
	public static URL url = null;
	private FileInputStream inputStream;

	String windowsDirectory = "";
	String unixDirectory = "";

	public String createFolder(String foldername, String rootDir) {

		File reportFolder = new File(rootDir + "/" + foldername);
		reportFolder.mkdir();

		return reportFolder.toString();
	}

	public String createDirectory() {

		Properties properties = new Properties();
		ExecutionLogger executionLoggerLogger = new ExecutionLogger();
		Logger logger = executionLoggerLogger.logger_start();

		try {

			codeSource = getClass().getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				url = new URL(codeSource.getLocation(), "conf/reportgenerator.properties");
			}

			inputStream = new FileInputStream(url.getFile());
			if (inputStream == null) {
				System.out.println("Sorry, unable to find ");
			}
			properties.load(inputStream);
			windowsDirectory = properties.getProperty("windowsDirectory");
			unixDirectory = properties.getProperty("unixDirectory");
		} catch (IOException e) {
			e.printStackTrace();
		}

		File reportFolder = null;
		String reportFolderName = "REPORTS";
		String osname = System.getProperty("os.name", "").toLowerCase();

		if (osname.startsWith("windows")) {

			System.out.println("Hello " + osname);

			reportFolder = new File(windowsDirectory + reportFolderName);
			reportFolder.exists();
			reportFolder.mkdir();
			logger.info(windowsDirectory + reportFolderName + " is created");

		} else if (osname.startsWith("linux")) {
			System.out.println("Hello " + osname);
			unixDirectory = properties.getProperty("unixDirectory");
			reportFolder = new File(unixDirectory + reportFolderName);
			reportFolder.exists();
			reportFolder.mkdir();
			logger.info(unixDirectory + reportFolderName + reportFolder + "directory is created");

		} else {
			System.out.println("Sorry, your operating system is different");
		}

		return reportFolder.toString();
	}
}
