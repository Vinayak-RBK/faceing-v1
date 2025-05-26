package com.iss.dao;

public class AdminUserPersonalDetailsDao {

	private String userId;
	private String name;
	private String email;
	private String dob;
	private String gender;
	private String height;
	private String weight;
	private String age;

	public AdminUserPersonalDetailsDao() {
		super();
	}

	public AdminUserPersonalDetailsDao(String userId, String name, String email, String dob, String gender,
			String height, String weight, String age) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.age = age;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "AdminUserPersonalDetailsDao [userId=" + userId + ", name=" + name + ", email=" + email + ", dob=" + dob
				+ ", gender=" + gender + ", height=" + height + ", weight=" + weight + ", age=" + age + "]";
	}

}
