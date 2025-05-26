package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.LegalSettings;

import jakarta.transaction.Transactional;

@Repository
public interface LegalSettingsRepository extends JpaRepository<LegalSettings, String>{
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE legal_setting SET content = :content,last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE legal_id = :legalId", nativeQuery = true)
	public int updateContentByLegalId(String content,String lastUpdateDate, String lastUpPName, Long legalId);

}
