package dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeliculaPersonajesDTO {
	private Long peliculaId;
	private String imagen;
	private String titulo;
	private String fecha;
	private String calificacion;
	private Set<PersonajeDTO> personajes  = new HashSet<>();
	private Set<GeneroDTO> generos = new HashSet<>();

}
