package com.rgenerator.excel;

import java.sql.SQLException;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;
import com.rgenerator.db.DbConnProvider;
import com.rgenerator.db.DbDataProvider;

import hierarchy.Hierarchy;
import vamTeam.ExecutionLogger;

public class ExcelSaveData {

	private DbConnProvider server;
	private Connection connection;
	private DbDataProvider dataProvider;
	private ExecutionLogger executionLogger;
	public static Logger LOGGER;

	public void startMonthlyReporting(LocalDate fromDate, LocalDate toDate) {
		executionLogger = new ExecutionLogger();
		LOGGER = executionLogger.logger_start();
		server = new DbConnProvider();
		server.connectionToFirstDB();
		connection = server.openConn();

		// add logger
		dataProvider = new DbDataProvider(connection);

		if (connection != null) {
			ResultSet monthlyHier = dataProvider.getMonthlyHierarchies(toDate.toString());
			Hierarchy monthly = new Hierarchy();
			try {
				while (monthlyHier.next()) {
					monthly.getHierarchyReport(monthlyHier.getString(1), fromDate, toDate, 2);
					LOGGER.info("execution montly report");
				}
			} catch (SQLException ex) {
				System.err.println("SQLException information");
				LOGGER.warning("SQLException information");
				while (ex != null) {
					System.err.println("Error msg: " + ex.getMessage());
					LOGGER.warning("Error msg: " + ex.getMessage());
					System.err.println("SQLSTATE: " + ex.getSQLState());
					LOGGER.warning("SQLSTATE: " + ex.getSQLState());
					System.err.println("Error code: " + ex.getErrorCode());
					LOGGER.warning("Error code: " + ex.getErrorCode());

					ex.printStackTrace();
					ex = ex.getNextException(); // For drivers that support chained exceptions
				}
			}
		}
		server.endConn(connection);

	}

	public void startWeeklyReporting(LocalDate fromDate, LocalDate toDate) {
		server = new DbConnProvider();
		server.connectionToFirstDB();
		connection = server.openConn();
		dataProvider = new DbDataProvider(connection);
		if (connection != null) {
			ResultSet weeklyHier = dataProvider.getWeeklyHierarchies(toDate.toString());
			Hierarchy weekly = new Hierarchy();
			try {
				while (weeklyHier.next()) {
					weekly.getHierarchyReport(weeklyHier.getString(1), fromDate, toDate, 1);
				}
			} catch (SQLException ex) {
				System.err.println("SQLException information");
				LOGGER.warning("SQLException information");
				while (ex != null) {
					System.err.println("Error msg: " + ex.getMessage());
					LOGGER.warning("Error msg: " + ex.getMessage());
					System.err.println("SQLSTATE: " + ex.getSQLState());
					LOGGER.warning("SQLSTATE: " + ex.getSQLState());
					System.err.println("Error code: " + ex.getErrorCode());
					LOGGER.warning("Error code: " + ex.getErrorCode());

					ex.printStackTrace();
					ex = ex.getNextException(); // For drivers that support chained exceptions
				}
			}
		}
		server.endConn(connection);
	}

}