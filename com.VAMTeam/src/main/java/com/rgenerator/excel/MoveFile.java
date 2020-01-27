package com.rgenerator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Logger;

import vamTeam.ExecutionLogger;

public class MoveFile {

	public CodeSource codeSource;
	public static URL url;
	private FileInputStream inputStream;

	public static Logger lOGGER;
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
		lOGGER = executionLoggerLogger.logger_start();

		try {

			codeSource = getClass().getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				url = new URL(codeSource.getLocation(), "application.properties");
			}

			inputStream = new FileInputStream(url.getFile());
			if (inputStream == null) {
				System.out.println("Sorry, unable to find ");
			}
			properties.load(inputStream);

			windowsDirectory = properties.getProperty("windowsDirectory");
			unixDirectory = properties.getProperty("unixDirectory");
		} catch (IOException e) {
			lOGGER.warning(e.getMessage());
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
			lOGGER.info(windowsDirectory + reportFolderName + " is created");

		} else if (osname.startsWith("linux")) {
			System.out.println("Hello " + osname);
			unixDirectory = properties.getProperty("unixDirectory");

			reportFolder = new File(unixDirectory + reportFolderName);
			reportFolder.exists();
			reportFolder.mkdir();
			lOGGER.info(unixDirectory + reportFolderName + reportFolder + " directory is created");

		} else {
			System.out.println("Sorry, your operating system is different");
		}

		return reportFolder.toString();
	}
}
