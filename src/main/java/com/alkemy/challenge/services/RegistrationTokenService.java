package com.alkemy.challenge.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.challenge.model.RegistrationTokenModel;
import com.alkemy.challenge.repositories.RegistrationTokenRepository;

@Service
public class RegistrationTokenService {
	
	@Autowired
	private RegistrationTokenRepository tokenRepo;
	
	public void saveRegistrationToken(RegistrationTokenModel token) {
		tokenRepo.save(token);
	}
	
	public Optional<RegistrationTokenModel> getToken(String token){
		return tokenRepo.findByToken(token);
	}

	public int updateConfirmedAt(String token) {
		return tokenRepo.updateConfirmedAt(token,LocalDateTime.now());
		
	}
}
