package com.alkemy.challenge.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alkemy.challenge.model.RegistrationTokenModel;
import com.alkemy.challenge.model.UserRol;
import com.alkemy.challenge.model.UsuarioModel;
import com.alkemy.challenge.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	RegistrationTokenService registrationTokenService;
	
	@Autowired
	EmailService emailUtil;
	
	public UsuarioModel saveUser(UsuarioModel userModel) { // Optional Usuario // Desaclopar  "registration service"
				
		UsuarioModel user = UsuarioModel.builder()
				.username(userModel.getUsername())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.userRol(UserRol.USER)
				.build();
		
		usuarioRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		RegistrationTokenModel registrationToken = new RegistrationTokenModel(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),user);
		registrationTokenService.saveRegistrationToken(registrationToken);
		
		String link = "http://localhost:8080/api/v1/register/confirm?token=" + token;
		log.info("Sending email ..."+userModel.getUsername());
		//emailUtil.send(userModel.getUsername(),"link token "+link); For send email -----------------
		
		return user;

	}
	// Creamos un metodo diferente para separar el admin del usuario ya que podriamos tener una funcionalidad futura y separada . Ademas de la practicidad
	public UsuarioModel saveUserAdmin(UsuarioModel userModel) {
		UsuarioModel user = UsuarioModel.builder()
				.username(userModel.getUsername())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.userRol(UserRol.ADMIN)
				.build();
		
		usuarioRepository.save(user);
		
		return user;
	}
	
	public Boolean isUsernameValid(String username) {
		
		return usuarioRepository.findByUsername(username).isEmpty();
	}
	public String confirmationToken(String token) {
		RegistrationTokenModel registrationToken = registrationTokenService
				.getToken(token)
				.orElseThrow(()->
					new IllegalStateException("Token empty"));
		
		if(registrationToken.getConfirmedAt()!=null) {
			 throw new IllegalStateException("email already confirmed");
		}
		LocalDateTime expiredAt = registrationToken.getExpiredAt();
		if (expiredAt.isBefore(LocalDateTime.now())) {
	            throw new IllegalStateException("token expired");
	    }
		//Enabled user
		registrationTokenService.updateConfirmedAt(token);
		usuarioRepository.updateEnabled(registrationToken.getUsuarioModel().getUsername());
		registrationToken.getUsuarioModel().getUsername();// setear el user a enable
		
		return "OK";
	}
}
