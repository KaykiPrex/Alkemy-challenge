package com.alkemy.challenge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alkemy.challenge.model.PeliculaoserieModel;
import com.alkemy.challenge.model.PersonajeModel;
import com.alkemy.challenge.repositories.PersonajeRepository;

import dto.PersonajeDTO;

@Service
public class PersonajeService {
	@Autowired
	PersonajeRepository personajeRepository;
	@Autowired
	private EntityManager entityManager;

	public ArrayList<PersonajeModel> findAll(Boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedCharacterFilter");
		filter.setParameter("isDeleted", isDeleted);
		ArrayList<PersonajeModel> personajes = (ArrayList<PersonajeModel>) personajeRepository.findAll();
		session.disableFilter("deletedCharacterFilter");
		return personajes;
	}

	// Se usa criteria api para realizar consultas dinamicas
	// Se usa filtros para ocultar los soft delete de manera dinamica
	public List<PersonajeModel> findAll(String nombre, String edad, String peso, String historia, Pageable pageable,
			Boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedCharacterFilter");
		filter.setParameter("isDeleted", isDeleted);

		List<PersonajeModel> characters = personajeRepository
				.findAll((Specification<PersonajeModel>) (root, cq, cb) -> {
					Predicate predicateMain = cb.conjunction();

					predicateMain = utils.DatabaseUtil.addPredicateLike(nombre, "nombre", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(edad, "edad", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(peso, "peso", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(historia, "historia", cb, root, predicateMain);
					cq.orderBy(cb.asc(root.get("nombre")));

					return predicateMain;
				}, pageable).getContent();

		session.disableFilter("deletedCharacterFilter");
		return characters;
	}
	
	public List<PersonajeDTO> findAllWithNameImg(String pelicula,String nombre, String edad, String peso, String historia, Pageable pageable,
			Boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedCharacterFilter");
		filter.setParameter("isDeleted", isDeleted);

		List<PersonajeModel> characters = personajeRepository
				.findAll((Specification<PersonajeModel>) (root, cq, cb) -> {
					Predicate predicateMain = cb.conjunction();
					
					if (!ObjectUtils.isEmpty(pelicula)) {
						Join<Object, Object> moviesJoin = root.join("peliculas");
						Predicate predicateNombre = cb.equal(moviesJoin.get("id"),pelicula);
						predicateMain = cb.and(predicateMain,predicateNombre);
					}					
					predicateMain = utils.DatabaseUtil.addPredicateLike(nombre, "nombre", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(edad, "edad", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(peso, "peso", cb, root, predicateMain);
					predicateMain = utils.DatabaseUtil.addPredicateLike(historia, "historia", cb, root, predicateMain);
					cq.orderBy(cb.asc(root.get("nombre")));

					return predicateMain;
				}, pageable).getContent();
		
		List<PersonajeDTO> listDtos = new ArrayList<>();
		
		characters.stream().forEach((c)->{
			listDtos.add(PersonajeDTO.builder(c.getNombre())
					.imagen(c.getImagen())
					.build() );									
		});
		
		session.disableFilter("deletedCharacterFilter");
		return listDtos;
	}

	public ArrayList<PersonajeModel> findAll() {
		return (ArrayList<PersonajeModel>) personajeRepository.findAll();
	}

	public Optional<PersonajeModel> findOne(Long id) {
		return personajeRepository.findById(id);
	}

	public PersonajeModel save(PersonajeModel personaje) {
		return personajeRepository.save(personaje);
	}

	public boolean exist(Long id) {
		return personajeRepository.findById(id).isPresent();
	}

	public void delete(Long id) {
		personajeRepository.deleteById(id);
	}
}
