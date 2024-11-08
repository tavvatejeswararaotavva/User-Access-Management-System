package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.database.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SoftwareServlet extends HttpServlet {

	public SoftwareServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verify if the user has the Admin role
		HttpSession session = request.getSession(false);
		if (session == null || !"Admin".equals(session.getAttribute("role"))) {
			response.getWriter().println("Access Denied: Only Admins can create software.");
			return;
		}

		// Retrieve form data
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String[] accessLevels = request.getParameterValues("accessLevel");

		// Combine selected access levels into a single comma-separated string
		String accessLevelsStr = String.join(",", accessLevels);

		// Insert software details into the database
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setString(3, accessLevelsStr);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				response.getWriter().println("Software added successfully!");
			} else {
				response.getWriter().println("Failed to add software.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred while adding software.");
		}
	}
}
