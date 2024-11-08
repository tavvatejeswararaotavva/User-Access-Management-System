package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:postgresql://localhost:5432/user_access_management";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "Teja@1998";

	// Method to get a database connection
	public static Connection getConnection() {
		Connection connection = null;
		try {
			// Load PostgreSQL JDBC driver (not always necessary, but good practice)
			Class.forName("org.postgresql.Driver");

			// Establishing connection
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("PostgreSQL JDBC Driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Failed to connect to the database.");
			e.printStackTrace();
		}
		return connection;
	}
}
