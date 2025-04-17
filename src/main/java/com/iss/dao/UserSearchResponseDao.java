package com.iss.dao;

import java.math.BigDecimal;

public class UserSearchResponseDao {

	private String name;
	private String gender;
	private BigDecimal weight;
	private BigDecimal height;
	private String email;
	private String dob;
	private String image;
	private String id;
	private boolean status;
	private float age;

	public UserSearchResponseDao() {
		super();
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

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserSearchResponseDao [name=" + name + ", gender=" + gender + ", weight=" + weight + ", height="
				+ height + ", email=" + email + ", dob=" + dob + ", image=" + image + ", id=" + id + ", status="
				+ status + ", age=" + age + "]";
	}

	public UserSearchResponseDao(String name, String gender, BigDecimal weight, BigDecimal height, String email,
			String dob, String image, String id, boolean status, float age) {
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
	}

}
