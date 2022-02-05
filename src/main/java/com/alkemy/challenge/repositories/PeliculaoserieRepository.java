package com.alkemy.challenge.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.model.PeliculaoserieModel;

@Repository
public interface PeliculaoserieRepository extends CrudRepository<PeliculaoserieModel, Long> ,JpaSpecificationExecutor<PeliculaoserieModel> {

}
