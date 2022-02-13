package com.alkemy.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alkemy.challenge.model.UserRol;
import com.alkemy.challenge.model.UsuarioModel;
import com.alkemy.challenge.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public UsuarioModel saveUser(UsuarioModel userModel) {
		UsuarioModel user = UsuarioModel.builder()
				.username(userModel.getUsername())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.userRol(UserRol.USER)
				.build();
		
		usuarioRepository.save(user);
		
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
}
