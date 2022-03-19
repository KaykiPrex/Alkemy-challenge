package com.alkemy.challenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario")
@Entity
public class UsuarioModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	private String name;
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserRol userRol;
	@Column(columnDefinition = "tinyint(1) default false")
	@Builder.Default
	private Boolean locked=false;
	@Column( columnDefinition = "tinyint(1) default false")
	@Builder.Default
	private Boolean enable=false;
	
}
