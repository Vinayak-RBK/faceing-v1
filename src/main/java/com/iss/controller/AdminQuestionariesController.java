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
import com.iss.service.AdminQuestionariesService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class AdminQuestionariesController {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;

	@Autowired
	private AdminQuestionariesService adminQuestionariesService;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private static final Logger logger = LoggerFactory.getLogger(AdminQuestionariesController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("/addQuestion")
	public ResponseEntity<?> addQuestionaries(@RequestBody BasicHealthQuestionDao dao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		BasicHealthQuestionDao enDao = new BasicHealthQuestionDao();

		try {

			enDao = (BasicHealthQuestionDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			_respDao = adminQuestionariesService.saveOnboardingQuestions(enDao, dateTimeFormat);

			logger.info("Adding Onborading question - {}", _respDao.getMessage());
			respObj.put("msg", _respDao.getMessage());
			respObj.put("success", _respDao.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred while adding onboarding Questions : {}", e);
			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("/deleteQuestion/{id}")
	public ResponseEntity<?> deleteQuestionaries(@PathVariable String id) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		try {
			// Deleting the On boarding question here
			_respDao = adminQuestionariesService.deleteQuestionaryByQuestionId(id);

			respObj.put("msg", _respDao.getMessage());
			respObj.put("success", _respDao.isSuccess());
			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Deleting onboarding question - {}", _respDao.getMessage());

			return new ResponseEntity<>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);
			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred while deleting onboarding Questions : {}", e);
			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/modifyQuestion")
	public ResponseEntity<Map<String, Object>> modifyQuestionaries(@RequestBody BasicHealthQuestionDao dao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		BasicHealthQuestionDao enDao = new BasicHealthQuestionDao();
		try {

			enDao = (BasicHealthQuestionDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			// Updating the on boarding question here
			_respDao = adminQuestionariesService.modifyQuestionaryByQuestionId(enDao, dateTimeFormat);

			respObj.put("message", _respDao.getMessage());
			respObj.put("success", _respDao.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Modifying onboarding question - {}", _respDao.getMessage());

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while modifying onboarding Questions : {}", e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

}
