package com.alkemy.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.model.UsuarioModel;
import com.alkemy.challenge.services.UsuarioService;

//#La pre autorizacion es otra forma para la configuracion de acesso de roles
//@PreAuthorize("authenticated")
@RestController
@RequestMapping(UsuarioEndpoint.AUTH)
public class UsuarioEndpoint {
	public static final String AUTH ="/auth";
	public static final String REGISTER ="/register";
	public static final String REGISTER_ADMIN ="/register-admin";
	@Autowired
	UsuarioService usuarioService;
	//@PreAuthorize("permitAll()")
	@PostMapping(UsuarioEndpoint.REGISTER)
	public ResponseEntity<UsuarioModel> createUser(@RequestBody UsuarioModel user ) {
		try {
			UsuarioModel u = usuarioService.saveUser(user);
			return new ResponseEntity<>(u, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// A modo de ejemplo se registra un admin por practicidad
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(UsuarioEndpoint.REGISTER_ADMIN)
	public ResponseEntity<UsuarioModel> createUserAdmin(@RequestBody UsuarioModel user ) {
		try {
			UsuarioModel u = usuarioService.saveUserAdmin(user);
			return new ResponseEntity<>(u, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
