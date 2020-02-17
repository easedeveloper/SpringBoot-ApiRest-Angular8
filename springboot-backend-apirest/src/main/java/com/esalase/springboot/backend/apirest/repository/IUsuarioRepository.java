package com.esalase.springboot.backend.apirest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esalase.springboot.backend.apirest.entity.ModelUsuario;

public interface IUsuarioRepository extends CrudRepository<ModelUsuario, Integer> {
	/*1ra opcion
	public ModelUsuario findByUsername(String username); 
	/*findBy, palabra clave reservada
	 * A traves del nombre del metodo(Query method name) se ejectuara la consulta JPQL:
	 * select u from Usuario u where u.username=?1
	 * si se quisiera buscar por otros parametros se colo en And
	 * public ModelUsuario findByUsernameAndEmail(String username, String email);
	 * */
	
	/* 2da opcion es igual a la primera*/
	@Query("select u from ModelUsuario u where u.username=?1")// selecciona al objeto Modelusuario from Modelusuario
	public ModelUsuario findByUsername2(String username);
	

}
