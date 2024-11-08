package com.model;

public class AccessRequest {
	private int id;
	private String employeeName;
	private String softwareName;
	private String accessType;
	private String reason;

	public AccessRequest(int id, String employeeName, String softwareName, String accessType, String reason) {
		this.id = id;
		this.employeeName = employeeName;
		this.softwareName = softwareName;
		this.accessType = accessType;
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public String getAccessType() {
		return accessType;
	}

	public String getReason() {
		return reason;
	}
}
