package com.iss.test;

public class Student {
	
	private String name;
	private String pincode;
	public Student(String name, String pincode) {
		super();
		this.name = name;
		this.pincode = pincode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", pincode=" + pincode + "]";
	}
	
	

}
