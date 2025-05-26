package com.iss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;

@Service
public interface BasicHealthQuestionService {

	  public List<BasicHealthQuestions> saveAll(List<BasicHealthQuestionDao> questionDtos, String dateTimeFormat);
	  
	  public List<BasicHealthQuestionDao> getAllOnBoardingHealthQuestions();
}
