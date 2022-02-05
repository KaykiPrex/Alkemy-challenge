package dto;

import java.time.LocalDate;

import com.alkemy.challenge.model.PeliculaoserieModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeliculaDTO {
	private Long id;
	private String imagen;
	private String titulo;
	private LocalDate fecha;
	private String calificacion;
	
	public PeliculaDTO(String titulo) {
		this.titulo=titulo;
	}
	
	public static PeliculaDTO from(PeliculaoserieModel pelicula) {
			PeliculaDTO peliculaDTO = new PeliculaDTO();
			peliculaDTO.setId(pelicula.getId());
			peliculaDTO.setImagen(pelicula.getImagen());
			peliculaDTO.setTitulo(pelicula.getTitulo());
			peliculaDTO.setFecha(pelicula.getFecha());
			peliculaDTO.setCalificacion(pelicula.getCalificacion());
		return peliculaDTO;
	}
	
	public static Builder builder(String titulo) {
		return new Builder(titulo);
	}
	
	public static class Builder{
		private PeliculaDTO peliculaDTO;
		
		private Builder(String titulo) {
			this.peliculaDTO = new PeliculaDTO(titulo);
		}
		
		public Builder id(Long id) {
			this.peliculaDTO.id = id;
			return this;
		}
		public Builder imagen(String imagen) {
			this.peliculaDTO.imagen = imagen;
			return this;
		}
		public Builder titulo(String titulo) {
			this.peliculaDTO.titulo = titulo;
			return this;
		}
		public Builder fecha(LocalDate fecha) {
			this.peliculaDTO.fecha = fecha;
			return this;
		}
		public Builder calificacion(String calificacion) {
			this.peliculaDTO.calificacion = calificacion;
			return this;
		}
		
		public PeliculaDTO build() {
			return new PeliculaDTO(this.peliculaDTO.id,this.peliculaDTO.imagen,this.peliculaDTO.titulo,this.peliculaDTO.fecha,this.peliculaDTO.calificacion);
		}
		
	}
}
