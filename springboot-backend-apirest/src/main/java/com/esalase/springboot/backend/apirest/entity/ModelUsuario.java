package com.esalase.springboot.backend.apirest.entity;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="tbl_usuarios")
public class ModelUsuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique=true, length = 20)
	private String username;
	
	@Column(length = 60)
	private String password;
	
	private Boolean enabled;
	
	private String nombre;
	
	private String apellido;
	
	@Column(unique=true)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)// CascadeType.ALL, cada que elimine al usuario va a eliminar a sus roles asignados y al crear va a guardar
	@JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id")
	, inverseJoinColumns = @JoinColumn(name="role_id")
	, uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
	/*name="users_authorities", indicamos el nombre de la tabla intermedia,
	  joinColumns = @JoinColumn(name="user_id"), indicamos el foreing key del dueño de la relacion
	  inverseJoinColumns = @JoinColumn(name="role_id"), la foreign key de la otra tabla relacionada
	*/	 
	private List<ModelRol> roles; //usuario va a tener una lista de tipo ModelRol

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<ModelRol> getRoles() {
		return roles;
	}

	public void setRoles(List<ModelRol> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	
	

}
