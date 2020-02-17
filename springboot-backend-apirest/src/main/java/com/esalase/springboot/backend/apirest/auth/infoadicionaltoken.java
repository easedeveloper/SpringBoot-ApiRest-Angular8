package com.esalase.springboot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.esalase.springboot.backend.apirest.entity.ModelUsuario;
import com.esalase.springboot.backend.apirest.service.IUsuarioService;

@Component
public class infoadicionaltoken implements TokenEnhancer{
	 //Se tiene que registrar este componente en el servidor de autorizacion AuthorizationServerConfig	
	
	@Autowired
	private IUsuarioService usuarioservice; 
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		ModelUsuario musuario =  usuarioservice.findByUsername(authentication.getName());
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("info_adicional", "Hola que tal".concat(authentication.getName()));
		
		info.put("nombre", musuario.getNombre());
		info.put("apellido", musuario.getApellido());
		info.put("email", musuario.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		/*Map<String, Object> y object porque el dato es generico
		 implementamos un objeto de tipo map de java implemetnado del hasmap y dentro se agrega nueva informacion
		 se le asiganara el info al acces point
		 y se le asignara al access token el cual vamos a retornar
		 ((DefaultOAuth2AccessToken) accessToken) se utilza para castear 
		*/
		return accessToken;
	}
	

}
