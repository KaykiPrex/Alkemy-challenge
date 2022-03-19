package com.alkemy.challenge.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.challenge.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel,Long> {
	public abstract Optional<UsuarioModel> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE UsuarioModel u SET u.enable = true WHERE u.username = ?1")
	public abstract void updateEnabled(String username);

}
