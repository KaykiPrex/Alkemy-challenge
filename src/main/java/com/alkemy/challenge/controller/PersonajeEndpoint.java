package com.alkemy.challenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.model.PeliculaoserieModel;
import com.alkemy.challenge.model.PersonajeModel;
import com.alkemy.challenge.services.PersonajeService;

import dto.PeliculaDTO;
import dto.PersonajeDTO;
import dto.PersonajePeliculasDTO;
import mapper.PersonajeMapper;

@RestController
@RequestMapping("/characters")
public class PersonajeEndpoint {
	@Autowired
	PersonajeService personajeService;

	PersonajeMapper mapper = Mappers.getMapper(PersonajeMapper.class);
	
	//Devuelve personajes segun la coincidencia de los atributos . En la paginacion no se utiliza Sort , entonces se lo inicializa de nuevo.
	@GetMapping()
	public ResponseEntity<ArrayList<PersonajeDTO>> getCharacters(@RequestParam(required = false) String movies, 
																 @RequestParam(required = false) String name,
																 @RequestParam(required = false) String age, 
																 @RequestParam(required = false) String weight, 
																 @RequestParam(required = false)String story,
																 								Pageable pageable) {
		try {
			PageRequest newPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
			List<PersonajeDTO> list = new ArrayList<>();
			list = personajeService.findAllWithNameImg(movies,name, age, weight, story, newPage, false);
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>( (ArrayList<PersonajeDTO>) list ,HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/details")
	public ResponseEntity<ArrayList<PersonajePeliculasDTO>> getActiveCharacters() {
		try {
			ArrayList<PersonajeModel> characters = personajeService.findAll(false);
			if (characters.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			ArrayList<PersonajePeliculasDTO> listDto = new ArrayList<PersonajePeliculasDTO>();
			listDto = (ArrayList<PersonajePeliculasDTO>) mapper.map(characters);
			return new ResponseEntity<>(listDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/search")
	public ResponseEntity<ArrayList<PersonajePeliculasDTO>> searchCharacters(
			@RequestParam(required = false) String nombre, String edad, String peso, String historia,
			Pageable pageable) {
		try {
			List<PersonajeModel> list = personajeService.findAll(nombre, edad, peso, historia, pageable, false);
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			ArrayList<PersonajePeliculasDTO> listDto = new ArrayList<PersonajePeliculasDTO>();
			listDto = (ArrayList<PersonajePeliculasDTO>) mapper.map(list);
			return new ResponseEntity<>(listDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonajePeliculasDTO> getCharacter(@PathVariable("id") Long id) {
		try {
			Optional<PersonajeModel> character = personajeService.findOne(id);
			if (character.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			PersonajePeliculasDTO dto = new PersonajePeliculasDTO();
			dto = mapper.personajeToPersonajeDTO(character.get());
			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}/movies")
	public ResponseEntity<Set<PeliculaDTO>> getMoviesOfCharacter(@PathVariable("id") Long id) {
		try {
			Optional<PersonajeModel> character = personajeService.findOne(id);
			if (character.isEmpty() || (character.get().getPeliculas().isEmpty())) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			PersonajePeliculasDTO dto = new PersonajePeliculasDTO();
			dto = mapper.personajeToPersonajeDTO(character.get());
			return new ResponseEntity<>(dto.getPeliculas(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}/movies/{movieId}")
	public ResponseEntity<PeliculaDTO> getMovieOfCharacter(@PathVariable("id") Long id,
			@PathVariable("movieId") Long movieId) {

		try {
			Optional<PersonajeModel> character = personajeService.findOne(id);
			if (character.isEmpty() || (character.get().getPeliculas().isEmpty())) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			Optional<PeliculaoserieModel> peliModel = character.get()
					.getPeliculas()
					.stream()
					.filter(p -> p.getId() == movieId)
					.findFirst();
			if (peliModel.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(PeliculaDTO.from(peliModel.get()), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/deleted")
	public ResponseEntity<ArrayList<PersonajePeliculasDTO>> getCharactersDeleted() {
		try {
			ArrayList<PersonajeModel> characters = personajeService.findAll(true);
			if (characters.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			ArrayList<PersonajePeliculasDTO> listDto = new ArrayList<PersonajePeliculasDTO>();
			listDto = (ArrayList<PersonajePeliculasDTO>) mapper.map(characters);
			return new ResponseEntity<>(listDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<ArrayList<PersonajePeliculasDTO>> getCharactersAll() {
		try {
			ArrayList<PersonajeModel> characters = personajeService.findAll();
			if (characters.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			ArrayList<PersonajePeliculasDTO> listDto = new ArrayList<PersonajePeliculasDTO>();
			listDto = (ArrayList<PersonajePeliculasDTO>) mapper.map(characters);
			return new ResponseEntity<>(listDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping()
	public ResponseEntity<PersonajeModel> createCharacters(@RequestBody PersonajeModel personaje ) {
		try {
			PersonajeModel p = personajeService.save(personaje);
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	public ResponseEntity<PersonajeModel> updateCharacters(@RequestBody PersonajeModel personaje) {
		try {
			if (personajeService.exist(personaje.getId())) {
				PersonajeModel p = personajeService.save(personaje);
				return new ResponseEntity<>(p, HttpStatus.OK);
			}
			return new ResponseEntity<>(personaje, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCharacter(@PathVariable final Long id) {
		try {
			personajeService.delete(id);
			return new ResponseEntity<>("Has been removed successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
