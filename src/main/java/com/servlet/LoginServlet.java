package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.database.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	public LoginServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// Valid user; retrieve role
				String role = rs.getString("role");

				// Start session and set user details
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("role", role);

				// Redirect based on role
				if ("Employee".equals(role)) {
					response.sendRedirect("requestAccess.jsp");
				} else if ("Manager".equals(role)) {
					response.sendRedirect("pendingRequests.jsp");
				} else if ("Admin".equals(role)) {
					response.sendRedirect("createSoftware.jsp");
				}
			} else {
				// Invalid credentials
				response.getWriter().println("Invalid username or password. Please try again.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred during login.");
		}
	}
}
