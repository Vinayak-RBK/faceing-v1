package com.iss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.repository.SequenceRepository;

@Service
public class SequenceGenerationServiceImpl implements SequenceGenerationService {
	
	@Autowired
	private SequenceRepository sequenceRepository;

	@Override
	public String generateSequenceServiceForEndUser() {
		String seq= sequenceRepository.getNextSequenceForEndUser();
		return seq;
	}

	@Override
	public String generateSequenceServiceForSuperAdmin() {
		String seq= sequenceRepository.getNextSequenceForSuperAdmin();
		return seq;
	}
	
}
