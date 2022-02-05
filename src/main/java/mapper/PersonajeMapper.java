package mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.alkemy.challenge.model.PersonajeModel;

import dto.PersonajePeliculasDTO;
@Mapper
public interface PersonajeMapper {
	@Mappings({
		@Mapping(target = "personajeId",source = "entity.id"),
		@Mapping(target = "imagen",source = "entity.imagen"),
		@Mapping(target = "nombre",source = "entity.nombre"),
		@Mapping(target = "edad",source = "entity.edad"),
		@Mapping(target = "peso",source = "entity.peso"),
		@Mapping(target = "historia",source = "entity.historia"),
		@Mapping(target = "peliculas",source = "entity.peliculas")
	})
	PersonajePeliculasDTO personajeToPersonajeDTO(PersonajeModel entity);
	@Mappings({
		@Mapping(target = "id",source = "dto.personajeId"),
		@Mapping(target = "imagen",source = "dto.imagen"),
		@Mapping(target = "nombre",source = "dto.nombre"),
		@Mapping(target = "edad",source = "dto.edad"),
		@Mapping(target = "peso",source = "dto.peso"),
		@Mapping(target = "historia",source = "dto.historia")
	})
	PersonajeModel personajeDTOtoPersonaje(PersonajePeliculasDTO dto);
	List<PersonajePeliculasDTO> map(List<PersonajeModel> personaje);
}
