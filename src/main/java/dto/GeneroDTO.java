package dto;

import com.alkemy.challenge.model.GeneroModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneroDTO {
	private Long id;
	private String nombre;
	private String imagen;
	
	public GeneroDTO(String nombre) {
		this.nombre=nombre;
	}
	
	public static GeneroDTO from(GeneroModel genero) {
		GeneroDTO generoDTO=new GeneroDTO();
		generoDTO.setId(genero.getId());
		generoDTO.setImagen(genero.getImagen());
		generoDTO.setNombre(genero.getNombre());
		return generoDTO;
	}
	
	
	public static class Builder{
		private GeneroDTO generoDTO;
		
		private Builder(String nombre) {
			this.generoDTO = new GeneroDTO(nombre);
		}
		
		public Builder id(Long id) {
			this.generoDTO.id = id;
			return this;
		}
		public Builder imagen(String imagen) {
			this.generoDTO.imagen = imagen;
			return this;
		}
		public Builder nombre(String nombre) {
			this.generoDTO.nombre=nombre;
			return this;
		}
	}
}
