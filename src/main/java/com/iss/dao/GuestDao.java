package com.iss.dao;

public class GuestDao {

	private String name;
	private String gender;
	private String dob;
	private String weight;
	private String height;
//	private String termCond;
//	private String image;
	private String userId;
	private String guestId;
	private String scannedDate;
	private String age;

	public GuestDao() {
		super();
	}

	public GuestDao(String name, String gender, String dob, String weight, String height, 
			String userId, String guestId, String scannedDate, String age) {
		super();
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
//		this.termCond = termCond;
//		this.image = image;
		this.userId = userId;
		this.guestId = guestId;
		this.scannedDate = scannedDate;
		this.age = age;
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

//	public String getTermCond() {
//		return termCond;
//	}
//
//	public void setTermCond(String termCond) {
//		this.termCond = termCond;
//	}
//
//	public String getImage() {
//		return image;
//	}
//
//	public void setImage(String image) {
//		this.image = image;
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public String getScannedDate() {
		return scannedDate;
	}

	public void setScannedDate(String scannedDate) {
		this.scannedDate = scannedDate;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "GuestDao [name=" + name + ", gender=" + gender + ", dob=" + dob + ", weight=" + weight + ", height="
				+ height +", userId=" + userId + ", guestId=" + guestId
				+ ", scannedDate=" + scannedDate + ", age=" + age + "]";
	}

}
