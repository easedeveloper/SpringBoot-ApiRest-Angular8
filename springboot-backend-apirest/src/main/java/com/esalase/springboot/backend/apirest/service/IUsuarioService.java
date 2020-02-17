package com.esalase.springboot.backend.apirest.service;

import com.esalase.springboot.backend.apirest.entity.ModelUsuario;

public interface IUsuarioService {
	
	public ModelUsuario findByUsername(String username);

}
