package com.rgenerator.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.ibm.db2.jcc.am.Connection;
import com.rgenerator.excel.ExcelSaveData;


public class DbConnProvider {

	public static Properties properties = new Properties();
	public static FileInputStream inputStream;
	private Logger logger = ExcelSaveData.LOGGER;

	public CodeSource codeSource;
	private String URL;
	private String USER;
	private String PASSWORD;
	private String PORT;
	private String DB_NAME;
	public static URL url;

	public DbConnProvider() {
	}

	public DbConnProvider(String URL, String USER, String PASSWORD) {
		this.URL = URL;
		this.USER = USER;
		this.PASSWORD = PASSWORD;

	}

	public void connectionToFirstDB() {

		try {

			codeSource = getClass().getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				url = new URL(codeSource.getLocation(), "conf/reportgenerator.properties");
				inputStream = new FileInputStream(url.getFile());

			}
			if (inputStream == null) {
				System.out.println("Sorry, unable to find ");
			}
			properties.load(inputStream);

			// Get data from application.properties file provide connection with First DB
			DB_NAME = properties.getProperty("dbNameForFirstConn");
			URL = properties.getProperty("dbUrlForFirstConn");
			USER = properties.getProperty("dbUserForFirstConn");

			PASSWORD = properties.getProperty("dbPassForFirstConn");
			PORT = properties.getProperty("dbPortForFirstConn");

			// Save new URL
			URL = URL + ":" + PORT + "/" + DB_NAME;
			new DbConnProvider(URL, USER, PASSWORD);
			inputStream.close();
		} catch (IOException e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}

	public void connectionToSecondtDB() {

		try {
			codeSource = getClass().getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				url = new URL(codeSource.getLocation(), "conf/reportgenerator.properties");
				inputStream = new FileInputStream(url.getFile());

			}
			inputStream = new FileInputStream(url.getFile());

			if (inputStream == null) {
				System.out.println("Sorry, unable to find ");
			}
			properties.load(inputStream);

			// Get data from application.properties file provide connection with Second DB
			DB_NAME = properties.getProperty("dbNameForSecondConn");
			URL = properties.getProperty("dbUrlForSecondConn");
			USER = properties.getProperty("dbUserForSecondConn");

			PASSWORD = properties.getProperty("dbPassForSecondConn");
			PORT = properties.getProperty("dbPortForSecondConn");

			// Save new URL
			URL = URL + ":" + PORT + "/" + DB_NAME;
			new DbConnProvider(URL, USER, PASSWORD);
			inputStream.close();
		} catch (IOException e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}

	public Connection openConn() {
		Connection connection = null;
		try {
			// loading DB2 driver
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			// System.out.println("**** Loaded the JDBC driver");

			// create connection
			connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);

			// Commit changes manually
			connection.setAutoCommit(false);

			// System.out.println("**** Created a JDBC connection to the data source");

		} catch (ClassNotFoundException e) {
			logger.warning(e.getMessage());
			logger.warning("Could not load JDBC driver");
			logger.warning("Exception: " + e);
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			logger.warning("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				logger.warning("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				logger.warning("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				logger.warning("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException(); // For drivers that support chained exceptions
			}

		}
		return connection;
	}

	public void endConn(Connection connection) {
		try {
			connection.commit();
			// Connection must be on a unit-of-work boundary to allow close
			connection.close();

		} catch (SQLException ex) {
			System.err.println("SQLException information");
			logger.warning("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				logger.warning("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				logger.warning("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				logger.warning("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException(); // For drivers that support chained exceptions
			}
		}

	}

}
