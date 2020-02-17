package com.esalase.springboot.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.esalase.springboot.backend.apirest.entity.ModelCliente;
import com.esalase.springboot.backend.apirest.entity.Modelregion;

public interface IclienteRepository extends JpaRepository<ModelCliente, Integer>{
	
	List<ModelCliente> findAll();
	
	
	@Query("from Modelregion")//se coloca el nombre de la clase entity, ya que se trabaja con objetos
	public List<Modelregion> listarRegion();

}
