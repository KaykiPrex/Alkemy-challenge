package com.alkemy.challenge.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "peliculaserie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeliculaoserieModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imagen;
	private String titulo;
	private LocalDate fecha;
	private String calificacion;

	@ManyToMany(fetch = FetchType.LAZY,cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
	        name = "rel_peliculas_personajes",
	        joinColumns = {@JoinColumn(name = "FK_PELICULASERIE", referencedColumnName = "id")},
	        inverseJoinColumns = {@JoinColumn(name="FK_PERSONAJE", referencedColumnName = "id")}
	    )
	@JsonIgnoreProperties("peliculas")
	private Set<PersonajeModel> personajes  = new HashSet<>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "rel_peliculas_generos",
	        joinColumns = {@JoinColumn(name = "FK_PELICULASERIE", referencedColumnName = "id")},
	        inverseJoinColumns = {@JoinColumn(name="FK_GENEROS", referencedColumnName = "id")}
	    )
	@JsonIgnoreProperties("peliculas")
	private Set<GeneroModel> generos  = new HashSet<>();
}
