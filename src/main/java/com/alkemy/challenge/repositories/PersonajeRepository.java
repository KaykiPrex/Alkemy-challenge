package com.alkemy.challenge.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.model.PersonajeModel;

@Repository
public interface PersonajeRepository extends CrudRepository<PersonajeModel, Long> ,JpaSpecificationExecutor<PersonajeModel> {

}
