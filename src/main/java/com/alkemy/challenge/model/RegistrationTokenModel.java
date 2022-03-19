package com.alkemy.challenge.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "token")
@Entity
public class RegistrationTokenModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private LocalDateTime createAt;
	@Column(nullable = false)
	private LocalDateTime expiredAt;
	private LocalDateTime confirmedAt;
	@ManyToOne
	@JoinColumn(name = "usuario_id" , nullable = false , updatable = false)
	private UsuarioModel usuarioModel;
	
	public RegistrationTokenModel(String token, LocalDateTime createAt, LocalDateTime expiredAt , UsuarioModel usuarioModel) {
		super();
		this.token = token;
		this.createAt = createAt;
		this.expiredAt = expiredAt;
		this.usuarioModel = usuarioModel;
	}
}
