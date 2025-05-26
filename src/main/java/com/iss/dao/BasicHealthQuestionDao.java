package com.iss.dao;

import java.util.Arrays;

public class BasicHealthQuestionDao {
	
	private String id;
	private String onBoardingQuestionName;
	private String[] onBoardingOptions;
	private String[] answer;
	
	public BasicHealthQuestionDao() {
		super();
	}

	public BasicHealthQuestionDao(String id, String onBoardingQuestionName, String[] onBoardingOptions,
			String[] answer) {
		super();
		this.id = id;
		this.onBoardingQuestionName = onBoardingQuestionName;
		this.onBoardingOptions = onBoardingOptions;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String[] getAnswer() {
		return answer;
	}

	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "BasicHealthQuestionDao [id=" + id + ", onBoardingQuestionName=" + onBoardingQuestionName
				+ ", onBoardingOptions=" + Arrays.toString(onBoardingOptions) + ", answer=" + answer + "]";
	}

	}
