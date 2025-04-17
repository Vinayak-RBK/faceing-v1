package com.iss.dao;


public class PersonalDetailsForAdminPanelDao {
	
	private String email;
	private String password;
	private String name;
	private String gender;
	private String dob;
	private double weight;
	private double height;
	private String image;
	
	public PersonalDetailsForAdminPanelDao() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "PersonalDetailsDao [email=" + email + ", password=" + password + ", name=" + name + ", gender=" + gender
				+ ", dob=" + dob + ", weight=" + weight + ", height=" + height + ", image=" + image + "]";
	}

	public PersonalDetailsForAdminPanelDao(String email, String password, String name, String gender, String dob, double weight,
			double height, String image) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.image = image;
	}

	
	
}
