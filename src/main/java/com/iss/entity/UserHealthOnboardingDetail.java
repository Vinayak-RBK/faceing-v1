package com.iss.entity;

import com.iss.converter.ItemListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users_Health_Onboarding_Details")
public class UserHealthOnboardingDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userHealthOnboardingDetailSeqGen")
	@SequenceGenerator(name = "userHealthOnboardingDetailSeqGen", sequenceName = "Users_Health_Onboarding_Details_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "id", length = 10, unique = true)
	private Long id;

	@Column(name = "user_Health_Onboarding_id", length = 10)
	private Integer userHealthOnboardingId;

	@Column(name = "on_boarding_question_name", length = 500)
	private String onboardingQuestionName;

	@Convert(converter = ItemListConverter.class)
	@Column(name = "on_boarding_answer_value", nullable = true, length = 200,columnDefinition = "JSON")
	private String[] onboardingAnswerValue;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private EndUser endUser;

	public UserHealthOnboardingDetail() {
		super();
	}

	public UserHealthOnboardingDetail(Long id, Integer userHealthOnboardingId, String onboardingQuestionName,
			String[] onboardingAnswerValue, EndUser endUser) {
		super();
		this.id = id;
		this.userHealthOnboardingId = userHealthOnboardingId;
		this.onboardingQuestionName = onboardingQuestionName;
		this.onboardingAnswerValue = onboardingAnswerValue;
		this.endUser = endUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserHealthOnboardingId() {
		return userHealthOnboardingId;
	}

	public void setUserHealthOnboardingId(Integer userHealthOnboardingId) {
		this.userHealthOnboardingId = userHealthOnboardingId;
	}

	public String getOnboardingQuestionName() {
		return onboardingQuestionName;
	}

	public void setOnboardingQuestionName(String onboardingQuestionName) {
		this.onboardingQuestionName = onboardingQuestionName;
	}

	public String[] getOnboardingAnswerValue() {
		return onboardingAnswerValue;
	}

	public void setOnboardingAnswerValue(String[] onboardingAnswerValue) {
		this.onboardingAnswerValue = onboardingAnswerValue;
	}

	public EndUser getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Override
	public String toString() {
		return "UserHealthOnboardingDetail [id=" + id + ", userHealthOnboardingId=" + userHealthOnboardingId
				+ ", onboardingQuestionName=" + onboardingQuestionName + ", onboardingAnswerValue="
				+ onboardingAnswerValue + ", endUser=" + endUser + "]";
	}

}
