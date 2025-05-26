package com.iss.dao;

public class PersonalDetailsForAdminPanelDao {

	private String email;
	private String password;
	private String name;
	private String gender;
	private String dob;
	private String weight;
	private String height;
	private String image;
	private String age;

	public PersonalDetailsForAdminPanelDao() {
		super();
	}

	public PersonalDetailsForAdminPanelDao(String email, String password, String name, String gender, String dob,
			String weight, String height, String image, String age) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.image = image;
		this.age = age;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonalDetailsForAdminPanelDao [email=" + email + ", password=" + password + ", name=" + name
				+ ", gender=" + gender + ", dob=" + dob + ", weight=" + weight + ", height=" + height + ", image="
				+ image + ", age=" + age + "]";
	}

}
