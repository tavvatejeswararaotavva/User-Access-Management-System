package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.database.DatabaseConnection;
import com.model.AccessRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ApprovalServlet extends HttpServlet {

	public ApprovalServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verify if the user has the Manager role
		HttpSession session = request.getSession(false);
		if (session == null || !"Manager".equals(session.getAttribute("role"))) {
			response.getWriter().println("Access Denied: Only Managers can approve/reject requests.");
			return;
		}

		// Fetch pending requests
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT r.id, u.username AS employeeName, s.name AS softwareName, r.access_type, r.reason "
					+ "FROM requests r " + "JOIN users u ON r.user_id = u.id "
					+ "JOIN software s ON r.software_id = s.id " + "WHERE r.status = 'Pending'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			ArrayList<AccessRequest> pendingRequests = new ArrayList<>();
			while (rs.next()) {
				AccessRequest requestObj = new AccessRequest(rs.getInt("id"), rs.getString("employeeName"),
						rs.getString("softwareName"), rs.getString("access_type"), rs.getString("reason"));
				pendingRequests.add(requestObj);
			}
			request.setAttribute("pendingRequests", pendingRequests);
			request.getRequestDispatcher("pendingRequests.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error fetching pending requests.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verify if the user is a manager
		HttpSession session = request.getSession(false);
		if (session == null || !"Manager".equals(session.getAttribute("role"))) {
			response.getWriter().println("Access Denied: Only Managers can approve/reject requests.");
			return;
		}

		// Parse action parameter
		String action = request.getParameter("action");
		String[] actionParts = action.split("_");
		String decision = actionParts[0];
		int requestId = Integer.parseInt(actionParts[1]);

		// Update request status in the databases
		try (Connection conn = DatabaseConnection.getConnection()) {
			String status = "approve".equals(decision) ? "Approved" : "Rejected";
			String sql = "UPDATE requests SET status = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setInt(2, requestId);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				response.getWriter()
						.println("Request " + (status.equals("Approved") ? "approved" : "rejected") + " successfully!");
			} else {
				response.getWriter().println("Failed to update request status.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred while updating request status.");
		}

		// Refresh pending requests
		response.sendRedirect("ApprovalServlet");
	}
}