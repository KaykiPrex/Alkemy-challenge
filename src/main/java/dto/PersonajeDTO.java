package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonajeDTO {
	private Long id;
	private String imagen;
	private String nombre;
	private String edad;
	private String peso;
	private String historia;
	
	public PersonajeDTO(long id, String nombre) {
		this.id=id;
		this.nombre=nombre;
	}
	public PersonajeDTO(String nombre) {
		this.nombre=nombre;
	}
	
	public static Builder builder(Long id,String nombre) {
		return new Builder(id,nombre);
	}
	
	public static Builder builder(String nombre) {
		return new Builder(nombre);
	}
	
	public static class Builder{
		private PersonajeDTO personajeDTO;
		
		private Builder(long id,String nombre) {
			this.personajeDTO = new PersonajeDTO(id,nombre);
		}
		
		private Builder(String nombre) {
			this.personajeDTO = new PersonajeDTO(nombre);
		}
		
		public Builder id(Long id) {
			this.personajeDTO.id = id;
			return this;
		}
		public Builder imagen(String imagen) {
			this.personajeDTO.imagen = imagen;
			return this ;
		}
		public Builder nombre(String nombre) {
			this.personajeDTO.nombre = nombre;
			return this;
		}
		public Builder edad(String edad) {
			this.personajeDTO.edad = edad;
			return this;
		}
		public Builder peso(String peso) {
			this.personajeDTO.peso = peso;
			return this;
		}
		public Builder historia(String historia) {
			this.personajeDTO.historia = historia;
			return this;
		}
		
		public PersonajeDTO build() {
			return new PersonajeDTO(this.personajeDTO.id,this.personajeDTO.imagen,this.personajeDTO.nombre,this.personajeDTO.edad,this.personajeDTO.peso,this.personajeDTO.historia);
		}
		
	}
}
