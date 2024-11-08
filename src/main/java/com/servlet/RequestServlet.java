package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.database.DatabaseConnection;
import com.model.Software;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RequestServlet extends HttpServlet {

	public RequestServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Ensure the user has the Employee role
		HttpSession session = request.getSession(false);
		if (session == null || !"Employee".equals(session.getAttribute("role"))) {
			response.getWriter().println("Access Denied: Only Employees can request access.");
			return;
		}

		// Fetch software list to populate the dropdown
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT id, name FROM software";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			ArrayList<Software> softwareList = new ArrayList<>();
			while (rs.next()) {
				Software software = new Software(rs.getInt("id"), rs.getString("name"));
				softwareList.add(software);
			}
			request.setAttribute("softwareList", softwareList);
			request.getRequestDispatcher("requestAccess.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error fetching software list.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Ensure the user is an employee
		HttpSession session = request.getSession(false);
		if (session == null || !"Employee".equals(session.getAttribute("role"))) {
			response.getWriter().println("Access Denied: Only Employees can request access.");
			return;
		}

		int userId = (int) session.getAttribute("userId"); // Assuming `userId` is stored in session
		int softwareId = Integer.parseInt(request.getParameter("softwareId"));
		String accessType = request.getParameter("accessType");
		String reason = request.getParameter("reason");

		// Insert access request into the database
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, softwareId);
			stmt.setString(3, accessType);
			stmt.setString(4, reason);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				response.getWriter().println("Access request submitted successfully!");
			} else {
				response.getWriter().println("Failed to submit access request.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred while submitting the request.");
		}
	}
}