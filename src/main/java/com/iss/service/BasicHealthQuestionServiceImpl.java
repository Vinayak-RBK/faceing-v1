package com.iss.service;

import org.modelmapper.ModelMapper;
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

	ModelMapper modelMapper = new ModelMapper();

	public List<BasicHealthQuestions> saveAll(List<BasicHealthQuestionDao> questionDtos, String dateTimeFormat) {
		// Convert DTOs to Entities before saving
//          List<BasicHealthQuestions> entities=new ArrayList<BasicHealthQuestions>();
		List<BasicHealthQuestions> entities = questionDtos.stream().map(dto ->
//            new BasicHealthQuestions(
//                dto.getHealthId(), 
//                dto.getHealthQuestion(), 
//                dto.getQuestionId(), 
//                dto.isOnSession()
//            )
		modelMapper.map(dto, BasicHealthQuestions.class)).collect(Collectors.toList());

//        BasicHealthQuestions ent=new BasicHealthQuestions();
//		
		for (BasicHealthQuestions ent : entities) {
			ent.setRegistDate(dateTimeFormat);
			ent.setRegistPName(this.getClass().getSimpleName());
			ent.setLastUpdateDate(dateTimeFormat);
			ent.setLastUpdatePName(this.getClass().getSimpleName());
			healthQuestionRepository.save(ent);
		}

//		String[] arr = {"Yamaha", "Bajaj", "TVS","Hero"};
////		ent.setHealthId(new Long(100));
//		ent.setOnBoardingOptions(arr);
//		ent.setOnBoardingQuestionName("Vinayak tell me");
//		ent.setOnBoardingQId("100");
//		ent.setRegistDate("Vin");
//		ent.setRegistPName("Vin");
//		ent.setLastUpdateDate("Vin");
//		ent.setLastUpdatePName("Vin");

//		healthQuestionRepository.save(ent);

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

		} catch (Exception e) {
			System.out.println(e);
		}

		return daoList;
	}
}
