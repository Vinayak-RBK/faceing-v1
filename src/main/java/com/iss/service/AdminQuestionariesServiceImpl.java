package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.entity.BasicHealthQuestions;
import com.iss.repository.BasicHealthQuestionRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminQuestionariesServiceImpl implements AdminQuestionariesService {
	
	@Value("${SUCCESSFULLY_CREATED_QUESTIONARY}")
	private String successCreatedQuestionary;
	
	@Value("${SOMETHING_WENT_WRONG}")
	private String somethingWentWrong;
	
	@Value("${ONBOARDING_QUESTIONS_NOTEXITS}")
	private String onbordingquestionExists;
	
	@Value("${DELETED_QUESTIONARY}")
	private String deletedQuestion;
	
	@Value("${UNABLE_MODIFY_QUESTIONARY}")
	private String unableModifyQuestionary;
	
	@Value("${SUCCESSFULLY_MODIFIED_QUESTIONARY}")
	private String successModifyQuestionary;

	@Autowired
	private BasicHealthQuestionRepository basicHQuestionsRepo;;

	private BasicHealthQuestions _basicHEnt = new BasicHealthQuestions();

	private AdminResponseDao _respDao = new AdminResponseDao();

	@Transactional
	@Override
	public AdminResponseDao saveOnboardingQuestions(BasicHealthQuestionDao dao, String dateTimeFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		_basicHEnt = new BasicHealthQuestions();
		_basicHEnt.setOnBoardingQuestionName(dao.getOnBoardingQuestionName());
		_basicHEnt.setOnBoardingOptions(dao.getOnBoardingOptions());
		_basicHEnt.setRegistDate(currentDate);
		_basicHEnt.setRegistPName(this.getClass().getSimpleName());
		_basicHEnt.setLastUpdateDate(currentDate);
		_basicHEnt.setLastUpdatePName(this.getClass().getSimpleName());

		BasicHealthQuestions ent = basicHQuestionsRepo.save(_basicHEnt);

		if (ent == null) {
			_respDao.setSuccess(false);
			_respDao.setMessage(somethingWentWrong);
		} else {
			_respDao.setSuccess(true);
			_respDao.setId(ent.getQuestionId().toString());
			_respDao.setMessage(successCreatedQuestionary);
		}
		return _respDao;
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public AdminResponseDao deleteQuestionaryByQuestionId(String questionId) {
		BasicHealthQuestions ent = basicHQuestionsRepo.findByQuestionId(new Long(questionId));

		if (ent == null) {
			_respDao.setSuccess(false);
			_respDao.setMessage(onbordingquestionExists);
		} else {
			basicHQuestionsRepo.deleteById(new Long(ent.getQuestionId()));
			_respDao.setId(questionId);
			_respDao.setSuccess(true);
			_respDao.setMessage(deletedQuestion);
		}

		return _respDao;
	}

	@Transactional
	@Override
	public AdminResponseDao modifyQuestionaryByQuestionId(BasicHealthQuestionDao dao, String dateTimeFormat,
			BasicHealthQuestions ent) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		if (ent == null) {
			_respDao.setSuccess(false);
			_respDao.setMessage(onbordingquestionExists);
		} else {

			BasicHealthQuestions healthEnt = new BasicHealthQuestions();

			healthEnt.setOnBoardingOptions(dao.getOnBoardingOptions());
			healthEnt.setOnBoardingQuestionName(dao.getOnBoardingQuestionName());
			healthEnt.setRegistDate(ent.getRegistDate());
			healthEnt.setRegistPName(ent.getRegistPName());
			healthEnt.setLastUpdateDate(currentDate);
			healthEnt.setLastUpdatePName(this.getClass().getSimpleName());

			BasicHealthQuestions resultEnt = basicHQuestionsRepo.save(healthEnt);

			if (resultEnt == null) {
				_respDao.setId(dao.getId());
				_respDao.setSuccess(false);
				_respDao.setMessage(unableModifyQuestionary);
			} else {
				_respDao.setId(resultEnt.getOnBoardingQId());
				_respDao.setSuccess(true);
				_respDao.setMessage(successModifyQuestionary);
			}
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
