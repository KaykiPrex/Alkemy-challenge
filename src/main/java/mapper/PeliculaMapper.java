package mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.alkemy.challenge.model.PeliculaoserieModel;
import com.alkemy.challenge.model.PersonajeModel;

import dto.PeliculaDTO;
import dto.PeliculaPersonajesDTO;
import dto.PersonajePeliculasDTO;
@Mapper
public interface PeliculaMapper {
	@Mappings({
		@Mapping(target = "peliculaId",source = "entity.id"),
		@Mapping(target = "imagen",source = "entity.imagen"),
		@Mapping(target = "titulo",source = "entity.titulo"),
		@Mapping(target = "fecha",source = "entity.fecha"),
		@Mapping(target = "calificacion",source = "entity.calificacion"),
		@Mapping(target = "personajes",source = "entity.personajes"),
		@Mapping(target = "generos",source = "entity.generos")
	})
	PeliculaPersonajesDTO peliculaToPeliculaDTO(PeliculaoserieModel entity);
	@Mappings({
		@Mapping(target = "id",source = "dto.peliculaId"),
		@Mapping(target = "imagen",source = "dto.imagen"),
		@Mapping(target = "titulo",source = "dto.titulo"),
		@Mapping(target = "fecha",source = "dto.fecha"),
		@Mapping(target = "calificacion",source = "dto.calificacion"),
		@Mapping(target = "generos",source = "dto.generos")
	})
	PeliculaoserieModel peliculaDTOtoPelicula(PeliculaPersonajesDTO dto);
	List<PeliculaPersonajesDTO> map(List<PeliculaoserieModel> pelicula);
	
	@Mappings({
		@Mapping(target = "id",source = "dto.id"),
		@Mapping(target = "imagen",source = "dto.imagen"),
		@Mapping(target = "titulo",source = "dto.titulo"),
		@Mapping(target = "fecha",source = "dto.fecha"),
		@Mapping(target = "calificacion",source = "dto.calificacion")
	})
	PeliculaoserieModel peliculaSimpleDTOtoPelicula(PeliculaDTO dto);
}
