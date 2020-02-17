package com.esalase.springboot.backend.apirest.entity;

import java.io.Serializable;
import java.sql.Date;
//import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_alumno")
public class ModelCliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int a_id;
	
	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 4, max = 12, message = "El tamaño tiene que estar entre 4 y 12")
	@Column(nullable = false)
	private String a_name;
	
	@NotEmpty(message = "No puede estar vacio")
	@Column(nullable = false)
	private String a_lastname;
	
	
	//@Email
	//@NotEmpty
	@NotNull(message = "no puede estar vacio")
	//@Temporal(TemporalType.DATE)
	@Column()
	private Date a_fechanaci;
	/*
	@PrePersist
	public void prePersis() {
		a_fechanaci = new Date();
	}*/
	@NotEmpty(message = "No puede estar vacio")
	@Column(nullable = false)
	private String a_tutor;
	
	private String foto;
	
	@NotNull(message = "no puede estar vacio")
	@ManyToOne(fetch=FetchType.LAZY)//caraga perezosa
	@JoinColumn(name="r_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Modelregion region;
	
	public Modelregion getregion() {
		return region;
	}
	public void setregion(Modelregion region) {
		this.region = region;
	}
	 
	public ModelCliente() {}
	

	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}

	public String getA_name() {
		return a_name;
	}

	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

	public String getA_lastname() {
		return a_lastname;
	}

	public void setA_lastname(String a_lastname) {
		this.a_lastname = a_lastname;
	}

	public Date getA_fechanaci() {
		return a_fechanaci;
	}

	public void setA_fechanaci(Date a_fechanaci) {
		this.a_fechanaci = a_fechanaci;
	}

	public String getA_tutor() {
		return a_tutor;
	}

	public void setA_tutor(String a_tutor) {
		this.a_tutor = a_tutor;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	
	
	
	
	

}
