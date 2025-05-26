package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;

@Service
public interface AdminQuestionariesService {
	
	public AdminResponseDao saveOnboardingQuestions(BasicHealthQuestionDao dao, String dateTimeFormat);
	
	public AdminResponseDao deleteQuestionaryByQuestionId(String questionId);
	
	public AdminResponseDao modifyQuestionaryByQuestionId(BasicHealthQuestionDao dao,String dateTimeFormat);
	
	public BasicHealthQuestions deleteRecord(Long id);

}
