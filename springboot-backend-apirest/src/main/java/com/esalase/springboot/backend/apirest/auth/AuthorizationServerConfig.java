package com.esalase.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passworEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	infoadicionaltoken infoadicionaltoken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//aqui se configura els entity las rutas de acceso
		// TODO Auto-generated method stub
		security.tokenKeyAccess("permitAll()")// se da permiso a cualquier usuario para identificarse
		.checkTokenAccess("isAuthenticated()") ; //permiso al endpoint que se encarga validar token
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients.inMemory().withClient("angularapp")
		.secret(passworEncoder.encode("12345"))
		.scopes("read","write")//permisos de lectura y escritura
		.authorizedGrantTypes("password","refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600); //tipo de consecion del token como vamos a tener el token
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenenhancerchain = new TokenEnhancerChain();
		tokenenhancerchain.setTokenEnhancers(Arrays.asList(  infoadicionaltoken, accessTokenConverter()));
		/*se va a crear una instancia del token inserchen es una cadena que va a unir la informacion del token por defecto
		 *que la obtenemos del accestokenconvert con esta nueva info adicional
		 */		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenenhancerchain);
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);//ES EL QUE FIRMA LA LLAVE PRIVADA
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);//EL QUE VALIDA Y VERIFICA ES LA PUBLICA
		/*ESTA ES LA IMPLEMENTACION PARA QUE NUESTOR CODIGO SECRETO NUESTRA LLAVES SEAN A TRAVES DEL ALGORITMO RSA,
		 * PRACTICAMENTE UN SISTEMA CRIPTOGRAFICO DE CLAVE PUBLICA Y PRIVADA PARA CIFRAR COMO PARA FIRMRAR DIGITALMENTE
		 * ENTONCES, CON LA RSA_PRIVATE FIRMAMOS EL TOKEN JWT Y CON LA RSA_PUBLIC VALIDAR PARA NUESTRO TOKEN SEAN AUNTETICOS  
		 */
		return jwtAccessTokenConverter;
	}
	
	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
