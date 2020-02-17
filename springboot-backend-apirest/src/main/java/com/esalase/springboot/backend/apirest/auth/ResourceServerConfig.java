package com.esalase.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/upload/img/**","/img/**").permitAll()
		/*.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")//PUEDE LLEVAR O NO EL PREFIJO ROLE_
		.antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ADMIN")
		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		se con el /** que cualquier ruta hacia adelante va a requerir permiso de administrador,
		 * ya que no se va a aplicar para todos lso metodos va a funcionar para PUT DELETE y para otros,
		 * aqui se incluye para ver regiones 
		 */
		.anyRequest()
		.authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
 
	/* Se dara acceso a api/clientes con reglas de oauth
	 * Da la autorizacion dependienpo del tipo de usuario ver, crear o actualizar
	 */
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
        /*acceso a cores, para el intercambio de recursos de origen cruzado que residen en distino dominios
         * Cors es un mecanismo que utiliza las cabeceras http, para permitir que un cliente esta en otro dominio 
         * distinto al backend tenga los permiso para acceder alos recursos del backend protegido por spring security
         * y por ouath*/
    }
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	/*Los dos ultimos metedos, son los endPoint para autenticarnos y obtener el token y para valida el token, y acceder alos recursos
	 * */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
