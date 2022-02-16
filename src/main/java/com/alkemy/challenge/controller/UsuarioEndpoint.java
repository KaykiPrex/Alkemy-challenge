package com.alkemy.challenge.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.model.UsuarioModel;
import com.alkemy.challenge.services.CustomUserDetailService;
import com.alkemy.challenge.services.JwtService;
import com.alkemy.challenge.services.UsuarioService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

//#La pre autorizacion es otra forma para la configuracion de acesso de roles
//@PreAuthorize("authenticated")
@RestController
@RequestMapping(UsuarioEndpoint.AUTH)
public class UsuarioEndpoint {
	public static final String AUTH ="/auth";
	public static final String REGISTER ="/register";
	public static final String REGISTER_ADMIN ="/register-admin";
	public static final String LOGIN ="/login";
	private static final Log logger = LogFactory.getLog(UsuarioEndpoint.class);
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	CustomUserDetailService userDetailService;
	@Autowired
	private JwtService jwtService;
	//@PreAuthorize("permitAll()")
	@PostMapping(UsuarioEndpoint.REGISTER)//Rearmar el registro y deshabilitar los filtros para esta uri
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
	@PostMapping(UsuarioEndpoint.LOGIN)// Rearmar el Login con los token
	public ResponseEntity<String> loginUser(@RequestBody UsuarioModel user) {
		try {	
		
			String token =jwtService.CreateToken(user.getUsername(),Arrays.asList("ROLE_USER"));
			
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
