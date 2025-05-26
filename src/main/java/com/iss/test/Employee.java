package com.iss.test;

public class Employee {
	private String empId;
	private String name;
	private String coutry;
	private String city;
	private String age;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(String empId, String name, String coutry, String city, String age) {
		super();
		this.empId = empId;
		this.name = name;
		this.coutry = coutry;
		this.city = city;
		this.age = age;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoutry() {
		return coutry;
	}

	public void setCoutry(String coutry) {
		this.coutry = coutry;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", name=" + name + ", coutry=" + coutry + ", city=" + city + ", age=" + age
				+ "]";
	}
	
}
