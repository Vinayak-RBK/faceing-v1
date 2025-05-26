package com.iss.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.BasicHealthQuestions;

import jakarta.transaction.Transactional;

@Repository
public interface BasicHealthQuestionRepository extends JpaRepository<BasicHealthQuestions, Long> {
	
	@Query(value = "Select * from basic_health_questions where question_id=?1", nativeQuery = true)
	public BasicHealthQuestions findByQuestionId(Long questionId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE basic_health_questions SET on_boarding_question_options = ?1, last_update_date = ?2,last_update_pname=?3 WHERE question_id = ?1", nativeQuery = true)
	public int updateByQuestionId(String[] questions,String lastUpdateDate, String lastUpPName, Long questionId);

}

