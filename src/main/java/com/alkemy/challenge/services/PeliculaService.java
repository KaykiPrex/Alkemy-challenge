package com.alkemy.challenge.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alkemy.challenge.model.PeliculaoserieModel;
import com.alkemy.challenge.repositories.PeliculaoserieRepository;

import dto.PeliculaDTO;
@Service
public class PeliculaService {
	@Autowired
	PeliculaoserieRepository pelicularepo;
	
	public ArrayList<PeliculaoserieModel> obtenerPeliculas(){
        return (ArrayList<PeliculaoserieModel>) pelicularepo.findAll();
    }
	
	public List<PeliculaDTO> findAllWithTitleImgDate(String title , String genreId, String order,Pageable pageable){
		List<PeliculaoserieModel> movies = pelicularepo.findAll((Specification<PeliculaoserieModel>) (root, cq, cb) -> {
			Predicate predicateMain = cb.conjunction();
			
			if (!ObjectUtils.isEmpty(genreId) ) {
				Join<Object, Object> genreJoin = root.join("generos");
				Predicate predicate= cb.equal(genreJoin.get("id"),genreId);
				predicateMain = cb.and(predicateMain,predicate);
			}					
			predicateMain = utils.DatabaseUtil.addPredicateMovieLike(title, "titulo", cb, root, predicateMain);
			
			if (!ObjectUtils.isEmpty(order) ) {
				if(order.equalsIgnoreCase("ASC")) {
					cq.orderBy(cb.asc(root.get("fecha")));				
				}
				if(order.equalsIgnoreCase("DESC")) {
					cq.orderBy(cb.desc(root.get("fecha")));				
				}
			}		

			return predicateMain;
		}, pageable).getContent();

		List<PeliculaDTO> listDtos = new ArrayList<>();
		
		movies.stream().forEach((m)->{
			listDtos.add(PeliculaDTO.builder(m.getTitulo())
					.imagen(m.getImagen())
					.fecha(m.getFecha())
					.build() );									
		});
		
        return listDtos;
    }
	public PeliculaoserieModel save(PeliculaoserieModel pelicula) {
		return pelicularepo.save(pelicula);
	}
	
	public boolean exist(Long id) {
		return pelicularepo.findById(id).isPresent();
	}

	public void delete(Long id) {
		pelicularepo.deleteById(id);
	}
	
}
