package com.iss.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_images")
public class UserImages {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userImageGen")
	@SequenceGenerator(name = "userImageGen", sequenceName = "user_image_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "image_id", length = 10, unique = true)
	private Long imageId;

	@Column(name = "image_path",  columnDefinition = "TEXT")
	private String imagePath;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private EndUser endUser;

	public UserImages() {
		super();
	}

	public UserImages(Long imageId, String imagePath, EndUser endUser) {
		super();
		this.imageId = imageId;
		this.imagePath = imagePath;
		this.endUser = endUser;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public EndUser getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Override
	public String toString() {
		return "UserImages [imageId=" + imageId + ", imagePath=" + imagePath + ", endUser=" + endUser + "]";
	}

}
