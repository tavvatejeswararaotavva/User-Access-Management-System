package com.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String role;

	// Constructor without id (useful for new users before they have an ID assigned
	// by the database)
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Constructor with id (useful for fetching users from the database)
	public User(int id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// Method to check if the user is an Employee
	public boolean isEmployee() {
		return "Employee".equalsIgnoreCase(this.role);
	}

	// Method to check if the user is a Manager
	public boolean isManager() {
		return "Manager".equalsIgnoreCase(this.role);
	}

	// Method to check if the user is an Admin
	public boolean isAdmin() {
		return "Admin".equalsIgnoreCase(this.role);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", role='" + role + '\'' + '}';
	}
}
