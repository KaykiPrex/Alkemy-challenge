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
public class PersonajePeliculasDTO {
	private Long personajeId;
	private String imagen;
	private String nombre;
	private String edad;
	private String peso;
	private String historia;
	private Set<PeliculaDTO> peliculas  = new HashSet<>();
}