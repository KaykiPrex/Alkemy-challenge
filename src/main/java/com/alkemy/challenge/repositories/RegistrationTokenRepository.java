package com.alkemy.challenge.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.challenge.model.RegistrationTokenModel;

@Repository
public interface RegistrationTokenRepository extends CrudRepository<RegistrationTokenModel,Long>{
	Optional<RegistrationTokenModel> findByToken(String token);
	@Transactional
	@Modifying
	@Query("UPDATE RegistrationTokenModel t SET t.confirmedAt = ?2 WHERE t.token = ?1")
	int updateConfirmedAt(String token, LocalDateTime now);

}
