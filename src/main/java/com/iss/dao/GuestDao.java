package com.iss.dao;

public class GuestDao {

	private String email;
	private String name;
	private String gender;
	private String dob;
	private double weight;
	private double height;
	private boolean termCond;
	private String image;
	private Long userId;
	private String guestId;

	public GuestDao() {
		super();
	}

	public GuestDao(String email, String name, String gender, String dob, double weight, double height,
			boolean termCond, String image, Long userId, String guestId) {
		super();
		this.email = email;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.termCond = termCond;
		this.image = image;
		this.userId = userId;
		this.guestId = guestId;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isTermCond() {
		return termCond;
	}

	public void setTermCond(boolean termCond) {
		this.termCond = termCond;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	@Override
	public String toString() {
		return "GuestDao [email=" + email + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", weight="
				+ weight + ", height=" + height + ", termCond=" + termCond + ", image=" + image + ", userId=" + userId
				+ ", guestId=" + guestId + "]";
	}

}
