package com.esalase.springboot.backend.apirest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esalase.springboot.backend.apirest.entity.ModelCliente;
import com.esalase.springboot.backend.apirest.entity.Modelregion;

public interface IServ_cliente {
	
	public List<ModelCliente> findAll();
	
	public Page<ModelCliente> findAll(Pageable pageable);
	
	public ModelCliente findById(int id);
	
	public ModelCliente save(ModelCliente mclie);
	
	public void delete(int id);
	
	public List<Modelregion> listarRegion();

}
