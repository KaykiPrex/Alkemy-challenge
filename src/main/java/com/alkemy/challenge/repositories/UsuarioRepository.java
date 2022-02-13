package com.alkemy.challenge.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel,Long> {
	public abstract UsuarioModel findByUsername(String username);

}
