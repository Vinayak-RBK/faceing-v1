package com.iss.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;
import com.iss.repository.BasicHealthQuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicHealthQuestionServiceImpl implements BasicHealthQuestionService {

	@Autowired
	private BasicHealthQuestionRepository healthQuestionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BasicHealthQuestionServiceImpl.class);


	ModelMapper modelMapper = new ModelMapper();

	public List<BasicHealthQuestions> saveAll(List<BasicHealthQuestionDao> questionDtos, String dateTimeFormat) {
		// Convert DTOs to Entities before saving
		List<BasicHealthQuestions> entities = questionDtos.stream().map(dto ->
		modelMapper.map(dto, BasicHealthQuestions.class)).collect(Collectors.toList());

		for (BasicHealthQuestions ent : entities) {
			ent.setRegistDate(dateTimeFormat);
			ent.setRegistPName(this.getClass().getSimpleName());
			ent.setLastUpdateDate(dateTimeFormat);
			ent.setLastUpdatePName(this.getClass().getSimpleName());
			healthQuestionRepository.save(ent);
		}

		logger.error("Saving Onboarding questions");
		// Save the list of entities
		return healthQuestionRepository.saveAll(entities);
	}

	@Override
	public List<BasicHealthQuestionDao> getAllOnBoardingHealthQuestions() {
		List<BasicHealthQuestionDao> daoList = new ArrayList<BasicHealthQuestionDao>();
		BasicHealthQuestionDao dao = new BasicHealthQuestionDao();
		List<BasicHealthQuestions> healthQueList = new ArrayList<BasicHealthQuestions>();

		try {
			healthQueList = healthQuestionRepository.findAll();

			for (BasicHealthQuestions ent : healthQueList) {
				dao = new BasicHealthQuestionDao();
				dao.setId(ent.getQuestionId().toString());
				dao.setOnBoardingOptions(ent.getOnBoardingOptions());
				dao.setOnBoardingQuestionName(ent.getOnBoardingQuestionName());
				daoList.add(dao);
			}
			
			logger.info("getting Onboarding questions : {}",daoList);

		} catch (Exception e) {
			logger.error("Exception occurred - {}",e);
		}

		return daoList;
	}
}
