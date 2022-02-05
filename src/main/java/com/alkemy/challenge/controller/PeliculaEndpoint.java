package com.alkemy.challenge.controller;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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
import com.alkemy.challenge.services.PeliculaService;

import dto.PeliculaDTO;
import dto.PeliculaPersonajesDTO;
import dto.PersonajeDTO;
import mapper.PeliculaMapper;

@RestController
@RequestMapping("/movies")
public class PeliculaEndpoint {
	@Autowired
	PeliculaService peliculaService;
	PeliculaMapper mapper = Mappers.getMapper(PeliculaMapper.class);
	
	//Devuelve peliculas ordenadas por "order" segun la coincidencia de "genre" y/o "title . En la paginacion no se utiliza Sort , entonces se lo inicializa de nuevo.
	@GetMapping()
	public ResponseEntity<ArrayList<PeliculaDTO>> getMoviesWithTitleImgDate(@RequestParam(required = false) String genre, 
															@RequestParam(required = false) String name, 
															@RequestParam(required = false) String order, 
																							Pageable pageable){
		try {
			PageRequest newPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()); 
			List<PeliculaDTO> movies = new ArrayList<>();
			if  (NumberUtils.isCreatable(genre)||ObjectUtils.isEmpty(genre)) {
				movies = peliculaService.findAllWithTitleImgDate(name,genre,order,newPage);					
			}
			if (movies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>( (ArrayList<PeliculaDTO>) movies ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/details")
	public ResponseEntity<ArrayList<PeliculaPersonajesDTO>> getMovies(){
		try {
			ArrayList<PeliculaoserieModel> movies = peliculaService.obtenerPeliculas();
			ArrayList<PeliculaPersonajesDTO> listDto = new ArrayList<PeliculaPersonajesDTO>();
			listDto = (ArrayList<PeliculaPersonajesDTO>) mapper.map(movies);
			if (movies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>( (ArrayList<PeliculaPersonajesDTO>) listDto ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping()
	public ResponseEntity<PeliculaoserieModel> createMovies(@RequestBody PeliculaoserieModel pelicula){
		try {System.out.println(pelicula.getTitulo());
			PeliculaDTO dto = PeliculaDTO.builder(pelicula.getTitulo())
					.imagen(pelicula.getImagen())
					.fecha(pelicula.getFecha())
					.calificacion(pelicula.getCalificacion())
					.build();
			
			PeliculaoserieModel p = peliculaService.save(mapper.peliculaSimpleDTOtoPelicula(dto));
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping()
	public ResponseEntity<PeliculaoserieModel> updateMovie(@RequestBody PeliculaoserieModel pelicula) {
		try {
			if (peliculaService.exist(pelicula.getId())) {
				PeliculaoserieModel p = peliculaService.save(pelicula);
				return new ResponseEntity<>(p, HttpStatus.OK);
			}
			return new ResponseEntity<>(pelicula, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable final Long id) {
		try {
			peliculaService.delete(id);
			return new ResponseEntity<>("Has been removed successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
