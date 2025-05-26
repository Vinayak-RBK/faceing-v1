package com.iss.dao;

public class UserSearchResponseDao {

	private String name;
	private String gender;
	private String weight;
	private String height;
	private String email;
	private String dob;
	private String image;
	private String id;
	private String status;
	private String age;
	private String password;
	private String role;

	public UserSearchResponseDao() {
		super();
	}

	public UserSearchResponseDao(String name, String gender, String weight, String height, String email, String dob,
			String image, String id, String status, String age, String password, String role) {
		super();
		this.name = name;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		this.email = email;
		this.dob = dob;
		this.image = image;
		this.id = id;
		this.status = status;
		this.age = age;
		this.password = password;
		this.role = role;
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

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	@Override
	public String toString() {
		return "UserSearchResponseDao [name=" + name + ", gender=" + gender + ", weight=" + weight + ", height="
				+ height + ", email=" + email + ", dob=" + dob + ", image=" + image + ", id=" + id + ", status="
				+ status + ", age=" + age + ", password=" + password + ", role=" + role + "]";
	}

}
