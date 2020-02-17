package com.esalase.springboot.backend.apirest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esalase.springboot.backend.apirest.entity.ModelUsuario;
import com.esalase.springboot.backend.apirest.repository.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService ,UserDetailsService{

	@Autowired
	private IUsuarioRepository repoUsuario;
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);//nos va a servir para capturar errores
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//1ro traemos al ModelUsuario, y traer el usuario a traves del username 
		ModelUsuario usuario = repoUsuario.findByUsername2(username);
		
		if (usuario == null) {
			logger.error("Error en el Login: no existe el usuasario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el Login: no existe el usuasario '"+username+"' en el sistema!"); //se lanza una excepcion
		}
		
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role-> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority-> logger.info("Role: "+ authority.getAuthority()))
				.collect(Collectors.toList());
		//.stream(), con este api de java 8, podemos obtener cada elemento del flujo y convertir a GrantedAuthority
		//tomamos la lista de roles usuario.getRoles(), luego lo convertimos a tipo a un collection List, pero del tipo SimpleGrantedAuthority   
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public ModelUsuario findByUsername(String username) {
		
		return repoUsuario.findByUsername2(username) ;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
