package com.iss.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;
import com.iss.service.AdminQuestionariesService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class AdminQuestionariesController {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;
	
	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Autowired
	private AdminQuestionariesService adminQuestionariesService;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private static final Logger logger = LoggerFactory.getLogger(AdminQuestionariesController.class);

	@PostMapping("/addQuestion")
	public ResponseEntity<AdminResponseDao> addQuestionaries(@RequestBody BasicHealthQuestionDao dao) {
		try {
			_respDao = adminQuestionariesService.saveOnboardingQuestions(dao, dateTimeFormat);

			if (_respDao.isSuccess()) {
				logger.info("Added onboarding question successfully...");
				return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.OK);
			} else {
				logger.info("Unable to create a record in the data base table.");
				return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Some thing went wrong while adding onboarding Questions : {}", e);
			_respDao.setSuccess(false);
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteQuestion/{id}")
	public ResponseEntity<AdminResponseDao> deleteQuestionaries(@PathVariable String id) {
		try {
			_respDao = adminQuestionariesService.deleteQuestionaryByQuestionId(id);
			if (_respDao.isSuccess()) {
				logger.info("Deeleted onboarding question successfully...");
				return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.OK);
			} else {
				logger.info("Unable to delete a record in the data base table.");
				return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.info("Some thing went wrong while deleting onboarding Questions : {}", e);
			_respDao.setSuccess(false);
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/modifyQuestion")
	public ResponseEntity<Map<String, Object>> modifyQuestionaries(@RequestBody BasicHealthQuestionDao dao) {
		Map<String, Object> respObj=new LinkedHashMap<String, Object>();
		try {
			// Deleting record first and creating same record again
			@SuppressWarnings("removal")
			BasicHealthQuestions ent=adminQuestionariesService.deleteRecord(new Long(dao.getId()));
			
			// here creating same record again with different id
			_respDao = adminQuestionariesService.modifyQuestionaryByQuestionId(dao,dateTimeFormat,ent);
			if (_respDao.isSuccess()) {
				respObj.put("message", _respDao.getMessage());
				respObj.put("success", _respDao.isSuccess());
				
				logger.info("Deeleted onboarding question successfully...");
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("message", _respDao.getMessage());
				respObj.put("success", _respDao.isSuccess());
				logger.info("Unable to modify a record in the data base table.");
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("message",someThingWentWrong);
			respObj.put("success", false);
			
			logger.info("Some thing went wrong while modifying onboarding Questions : {}", e);
			_respDao.setSuccess(false);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

}
