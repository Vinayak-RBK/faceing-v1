package com.iss.entity;

import com.iss.converter.ItemListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Basic_Health_Questions")
public class BasicHealthQuestions {

	@Id
	@Column(name = "Question_id", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basicHealthQuestionsSeqGen")
	@SequenceGenerator(name = "basicHealthQuestionsSeqGen", sequenceName = "Basic_Health_Questions_sequence", initialValue = 1000000000, allocationSize = 1)
	private Long questionId;

	@Column(name = "on_boarding_QID",columnDefinition = "TEXT")
	private String onBoardingQId;
	
	@Column(name = "on_boarding_question_name",columnDefinition = "TEXT")
	private String onBoardingQuestionName;
	
	@Convert(converter = ItemListConverter.class)
	@Column(name = "on_boarding_question_options", nullable = true, length = 200,columnDefinition = "JSON")
	private String[] onBoardingOptions;
	
	@Column(name = "regist_date",columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname",columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date",columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname",columnDefinition = "TEXT")
	private String lastUpdatePName;

	public BasicHealthQuestions() {
		super();
	}

	public BasicHealthQuestions(Long questionId, String onBoardingQId, String onBoardingQuestionName,
			String[] onBoardingOptions, String registDate, String registPName, String lastUpdateDate,
			String lastUpdatePName) {
		super();
		this.questionId = questionId;
		this.onBoardingQId = onBoardingQId;
		this.onBoardingQuestionName = onBoardingQuestionName;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getOnBoardingQId() {
		return onBoardingQId;
	}

	public void setOnBoardingQId(String onBoardingQId) {
		this.onBoardingQId = onBoardingQId;
	}

	public String getOnBoardingQuestionName() {
		return onBoardingQuestionName;
	}

	public void setOnBoardingQuestionName(String onBoardingQuestionName) {
		this.onBoardingQuestionName = onBoardingQuestionName;
	}

	public String[] getOnBoardingOptions() {
		return onBoardingOptions;
	}

	public void setOnBoardingOptions(String[] onBoardingOptions) {
		this.onBoardingOptions = onBoardingOptions;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getRegistPName() {
		return registPName;
	}

	public void setRegistPName(String registPName) {
		this.registPName = registPName;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdatePName() {
		return lastUpdatePName;
	}

	public void setLastUpdatePName(String lastUpdatePName) {
		this.lastUpdatePName = lastUpdatePName;
	}

	@Override
	public String toString() {
		return "BasicHealthQuestions [questionId=" + questionId + ", onBoardingQId=" + onBoardingQId
				+ ", onBoardingQuestionName=" + onBoardingQuestionName + ", onBoardingOptions="
				+  ", registDate=" + registDate + ", registPName=" + registPName
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + "]";
	}
}
