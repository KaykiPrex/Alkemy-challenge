package com.alkemy.challenge.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alkemy.challenge.model.UsuarioModel;
import com.alkemy.challenge.repositories.UsuarioRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UsuarioRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UsuarioModel userModel = userRepo.findByUsername(username);
		if (userModel == null) {
            throw new UsernameNotFoundException(username);
        }
		String role = "ROLE_"+userModel.getUserRol().toString();
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		UserDetails user = User.withUsername(userModel.getUsername())
				.password(userModel.getPassword())
				.authorities(role)
				.build();
		return user;
	}
	
	/*public UsuarioModel saveUser(UsuarioModel userModel) {
		UsuarioModel user = UsuarioModel.builder()
				.username(userModel.getUsername())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.build();
		return user;
	}*/

}
