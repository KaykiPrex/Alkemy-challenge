package com.alkemy.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "genero")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneroModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imagen;
	private String nombre;
	@ManyToMany(mappedBy = "generos", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"personajes","generos"})
	private Set<PeliculaoserieModel> peliculas  = new HashSet<>();
}
