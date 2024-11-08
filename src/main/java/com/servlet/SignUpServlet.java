package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.database.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignUpServlet() {
		System.out.println("SignUpServlet Constructor Called");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Ensure both username and password are provided
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			response.getWriter().println("Username and password must not be empty.");
			return;
		}

		// Insert the new user into the database with default role "Employee"
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Employee')";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				response.setContentType("text/html");
				response.getWriter().println("Registration successful! You can now <a href='login.jsp'>log in</a>.");
			} else {
				response.setContentType("text/html");
				response.getWriter()
						.println("Registration failed, please try again. <a href='signup.jsp'>Go back to Sign Up</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred during registration.");
		}
	}
}
