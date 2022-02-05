package com.alkemy.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personaje")
@SQLDelete(sql = "UPDATE personaje SET deleted = true WHERE id=?")
@FilterDef(name = "deletedCharacterFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedCharacterFilter", condition = "deleted = :isDeleted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonajeModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imagen;
	private String nombre;
	private String edad;
	private String peso;
	private String historia;
	private Boolean deleted = Boolean.FALSE;
	@ManyToMany(mappedBy = "personajes", fetch = FetchType.LAZY,cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JsonIgnoreProperties("personajes")
	private Set<PeliculaoserieModel> peliculas  = new HashSet<>();
}
