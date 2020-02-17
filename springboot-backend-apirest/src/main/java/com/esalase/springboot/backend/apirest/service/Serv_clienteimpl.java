package com.esalase.springboot.backend.apirest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.esalase.springboot.backend.apirest.entity.ModelCliente;
import com.esalase.springboot.backend.apirest.entity.Modelregion;
import com.esalase.springboot.backend.apirest.repository.IclienteRepository;


@Service
public class Serv_clienteimpl implements IServ_cliente{
	
	@Autowired
	private IclienteRepository clirepo;
	
	@Override	
	public List<ModelCliente> findAll() {
		
		return clirepo.findAll();
	}
	
	@Override
	public Page<ModelCliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clirepo.findAll(pageable);
	}

	@Override
	public ModelCliente findById(int id) {
		return clirepo.findById(id).orElse(null);
	}

	@Override
	public ModelCliente save(ModelCliente mclie) {
		// TODO Auto-generated method stub
		return clirepo.save(mclie);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		clirepo.deleteById(id);
		
	}
	
	@Override
	public List<Modelregion> listarRegion() {
		return clirepo.listarRegion();
	}

	

}
