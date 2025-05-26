package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;
import com.iss.repository.BasicHealthQuestionRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class AdminQuestionariesServiceImpl implements AdminQuestionariesService {

	@Value("${SUCCESSFULLY_CREATED_QUESTIONARY}")
	private String successCreatedQuestionary;

	@Value("${SOMETHING_WENT_WRONG}")
	private String somethingWentWrong;

	@Value("${ONBOARDING_QUESTIONS_NOTEXITS}")
	private String onbordingquestionDoesnotExists;

	@Value("${DELETED_QUESTIONARY}")
	private String deletedQuestion;

	@Value("${UNABLE_MODIFY_QUESTIONARY}")
	private String unableModifyQuestionary;

	@Value("${SUCCESSFULLY_MODIFIED_QUESTIONARY}")
	private String successModifyQuestionary;
	
	@Value("${SECRET_KEY}")
	private String secretKey;
	
	@Value("${SECRET_IV}")
	private String secretIv;
	
	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private BasicHealthQuestionRepository basicHQuestionsRepo;;

	private BasicHealthQuestions _basicHEnt = new BasicHealthQuestions();

	private AdminResponseDao _respDao = new AdminResponseDao();
	
	private static final Logger logger = LoggerFactory.getLogger(AdminQuestionariesServiceImpl.class);

	@Transactional
	@Override
	public AdminResponseDao saveOnboardingQuestions(BasicHealthQuestionDao dao, String dateTimeFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		
		BasicHealthQuestions enBasicHEnt=new BasicHealthQuestions();
		_basicHEnt = new BasicHealthQuestions();
		_basicHEnt.setOnBoardingQuestionName(dao.getOnBoardingQuestionName());
		_basicHEnt.setOnBoardingOptions(dao.getOnBoardingOptions());
		_basicHEnt.setRegistDate(currentDate);
		_basicHEnt.setRegistPName(this.getClass().getSimpleName());
		_basicHEnt.setLastUpdateDate(currentDate);
		_basicHEnt.setLastUpdatePName(this.getClass().getSimpleName());
		
		try {
			enBasicHEnt=(BasicHealthQuestions) EncryptedDecryptedObjectUtil.getEncryptedObject(_basicHEnt,  secretKey, secretIv,
						isEncryptDecryptDatabaseData);
		} catch (Exception e) {
			_respDao.setSuccess(Boolean.toString(false));
			_respDao.setMessage(somethingWentWrong);
			
			logger.error("Exception occured While saving onboarding question - {}",e);
			return _respDao;
		}

		BasicHealthQuestions ent = basicHQuestionsRepo.save(enBasicHEnt);

		if (ent == null) {
			_respDao.setSuccess(Boolean.toString(false));
			_respDao.setMessage(somethingWentWrong);
			
		} else {
			_respDao.setSuccess(Boolean.toString(true));
			_respDao.setId(ent.getQuestionId().toString());
			_respDao.setMessage(successCreatedQuestionary);
			
		}
		
		logger.info("While saving onboarding question - {}",_respDao.getMessage());
		return _respDao;
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public AdminResponseDao deleteQuestionaryByQuestionId(String questionId) {
		BasicHealthQuestions ent = basicHQuestionsRepo.findByQuestionId(new Long(questionId));

		if (ent == null) {
			_respDao.setSuccess(Boolean.toString(false));
			_respDao.setMessage(onbordingquestionDoesnotExists);
		} else {
			basicHQuestionsRepo.deleteById(new Long(ent.getQuestionId()));
			_respDao.setId(questionId);
			_respDao.setSuccess(Boolean.toString(true));
			_respDao.setMessage(deletedQuestion);
		}

		logger.info("While deleting onboarding question - {}",_respDao.getMessage());
		return _respDao;
	}

	@Transactional
	@Override
	public AdminResponseDao modifyQuestionaryByQuestionId(BasicHealthQuestionDao dao, String dateTimeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		String[] enOnboardingOptions= {};
		String enOnboardQName="";
		String encurrentDate="";
		String enUpdatePName="";

		@SuppressWarnings("removal")
		BasicHealthQuestions basicHealthQuestion = basicHQuestionsRepo.findByQuestionId(new Long(dao.getId()));

		if (basicHealthQuestion == null) {
			_respDao.setSuccess(Boolean.toString(false));
			_respDao.setMessage(onbordingquestionDoesnotExists);
			return _respDao;
		} else {
			
			try {
				 enOnboardingOptions= (String[]) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getOnBoardingOptions(), secretKey, secretIv,
							isEncryptDecryptDatabaseData);
				 enOnboardQName= (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getOnBoardingQuestionName(), secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				 encurrentDate= (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				 enUpdatePName= (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(), secretKey, secretIv,
						isEncryptDecryptDatabaseData);
			} catch (Exception e) {
				_respDao.setSuccess(Boolean.toString(false));
				_respDao.setMessage(somethingWentWrong);
				return _respDao;
			}
			
			basicHealthQuestion.setQuestionId(basicHealthQuestion.getQuestionId());
			basicHealthQuestion.setOnBoardingOptions(enOnboardingOptions);
			basicHealthQuestion.setOnBoardingQuestionName(enOnboardQName);
			basicHealthQuestion.setLastUpdateDate(encurrentDate);
			basicHealthQuestion.setLastUpdatePName(enUpdatePName);

			BasicHealthQuestions resultEnt = basicHQuestionsRepo.save(basicHealthQuestion);

			if (resultEnt == null) {
				_respDao.setId(dao.getId());
				_respDao.setSuccess(Boolean.toString(false));
				_respDao.setMessage(unableModifyQuestionary);
			} else {
				_respDao.setId(resultEnt.getOnBoardingQId());
				_respDao.setSuccess(Boolean.toString(true));
				_respDao.setMessage(successModifyQuestionary);
			}
			
			logger.info("While Modifying onboarding question - {}",_respDao.getMessage());
		}

		return _respDao;
	}

	@Transactional
	@Override
	public BasicHealthQuestions deleteRecord(Long id) {
		BasicHealthQuestions ent = basicHQuestionsRepo.findByQuestionId(id);

		if (ent != null) {
			basicHQuestionsRepo.deleteById(id);
		}

		return ent;
	}

}
