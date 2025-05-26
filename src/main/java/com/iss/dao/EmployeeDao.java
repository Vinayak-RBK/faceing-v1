package com.iss.dao;

public class EmployeeDao {
	
	private String id;
	private String name;
	private String jobRole;
	private String email;
	private String password;
	private String mobileNumber;
	
	public EmployeeDao() {
		super();
	}

	public EmployeeDao(String id, String name, String jobRole, String email, String password, String mobileNumber) {
		super();
		this.id = id;
		this.name = name;
		this.jobRole = jobRole;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "EmployeeDao [id=" + id + ", name=" + name + ", jobRole=" + jobRole + ", email=" + email + ", password="
				+ password + ", mobileNumber=" + mobileNumber + "]";
	}
}
