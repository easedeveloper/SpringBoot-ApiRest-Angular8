package com.esalase.springboot.backend.apirest.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.esalase.springboot.backend.apirest.entity.ModelCliente;
import com.esalase.springboot.backend.apirest.entity.Modelregion;
import com.esalase.springboot.backend.apirest.service.IServ_cliente;
import com.esalase.springboot.backend.apirest.service.Iuploadfileservice;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class Cliente_Rest_Controller {
	
	@Autowired
	private IServ_cliente servclien;
	
	@Autowired
	private Iuploadfileservice uploadservice;
	
	private final Logger log = LoggerFactory.getLogger(Cliente_Rest_Controller.class);
	
	@GetMapping("/clientes")
	public List<ModelCliente> index(){
		return servclien.findAll();
	}
	
	@GetMapping("/clientes/page/{page}")
	public Page<ModelCliente> index(@PathVariable Integer page){
		
		Pageable pageable = PageRequest.of(page, 5);//4 registros por paginas
		
		return servclien.findAll(pageable);
	}
	
	
	
	//ResponseEntity<?> es de tipo generico, se puede colocar el ModelCliente
	//@ResponseStatus(HttpStatus.OK)
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes/{id}")	
	public ResponseEntity<?> showid(@PathVariable int id) {
		ModelCliente cliente = null; 
		Map<String, Object> response = new HashMap<>();		
		
		try {
			 cliente  = servclien.findById(id);
		} catch(DataAccessException e) {
			response.put("msjerror", "Error al Realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (cliente == null) {
			response.put("msjerror", "El cliente no Existe");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} 
		
		return new ResponseEntity<ModelCliente>(cliente,HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updatecli(@Valid @RequestBody ModelCliente mclie, BindingResult result, @PathVariable int id) {
		ModelCliente updatecli = servclien.findById(id);
		ModelCliente actualcli = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			
			 // ->, funcion lambda ode flecha. Esto es para hacer un manejo de errores
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err->"El campo'"+ err.getField()+ "' "+ err.getDefaultMessage())//aca nuestro flujo esta convertido a string
					.collect(Collectors.toList());//a podemos convertir de regreso el stream a un tipo List 
			
			/*crear una lista de errores forma al utilizar al JDK8
			List<String> errors =new ArrayList<>();
			for(FieldError err: result.getFieldErrors()) {
				errors.add("El Campo '"+ err.getField() +"' "+ err.getDefaultMessage());
			}
			*/
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (updatecli == null) {
			response.put("msjerror", "El cliente no se pudo Actualizar");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			updatecli.setA_name(mclie.getA_name());
			updatecli.setA_lastname(mclie.getA_lastname());
			updatecli.setA_fechanaci(mclie.getA_fechanaci());
			updatecli.setA_tutor(mclie.getA_tutor());
			updatecli.setregion(mclie.getregion());
			
			actualcli =  servclien.save(updatecli);
			
		} catch (DataAccessException e) {
			response.put("msjerror", "Error al Actualizar en la Base de Datos");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("msjexito", "El Cliente ha sido Actualizado con exito!");
		response.put("Cliente", actualcli);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)//Intersepta el objeto cliente y valida cada valor cada atributo desde el requestbody en Json
	public ResponseEntity<?> crearcliente(@Valid @RequestBody ModelCliente mclie, BindingResult result) {
		//mclie.setA_fechanaci(new Date());
		
		ModelCliente newcliente = null;
		Map<String, Object> response = new HashMap<>();	
		
		if (result.hasErrors()) {
			
			 // ->, funcion lambda ode flecha. Esto es para hacer un manejo de errores
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err->"El campo'"+ err.getField()+ "' "+ err.getDefaultMessage())//aca nuestro flujo esta convertido a string
					.collect(Collectors.toList());//a podemos convertir de regreso el stream a un tipo List 
			
			/*crear una lista de errores forma al utilizar al JDK8
			List<String> errors =new ArrayList<>();
			for(FieldError err: result.getFieldErrors()) {
				errors.add("El Campo '"+ err.getField() +"' "+ err.getDefaultMessage());
			}
			*/
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		} 
		
		try {
			newcliente = servclien.save(mclie);			
		} catch (DataAccessException e) {
			response.put("msjerror", "Error al Insertar en la Base de Datos");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("msjcreado", "El Cliente ha sido creado con exito!");
		response.put("Cliente", newcliente);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteclie(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			ModelCliente cliente = servclien.findById(id);
			//obtenemos el cliente por id
			String nombrefotoanterior = cliente.getFoto();
			//Obtenemos la foto anterior
			
			uploadservice.eliminar(nombrefotoanterior);
			
			/* ESTO ESTA IMPLEMENTADO EN  IuploadfileService 
			 * if (nombrefotoanterior != null && nombrefotoanterior.length() >0) {
				//si el cliente que estamos eliminando tiene una imagen que esta guardado en su registro, se borra
				Path rutafotoanterior = Paths.get("uploadfotos").resolve(nombrefotoanterior).toAbsolutePath();
				//se obtiene la ruta completa y el archivo
				File archivofotoanterior = rutafotoanterior.toFile();
				//convertir a un objeto de tipo File
				if ( archivofotoanterior.exists() && archivofotoanterior.canRead()) {
					//ahora validamos que este archivo exista se puede leer y se elimine
					archivofotoanterior.delete();
				}				
			}			
			*/
			servclien.delete(id);			
		} catch (DataAccessException e) {
			response.put("msjerror", "Error al Eliminar en la Base de Datos");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("msjerror", "El Cliente ha sido Eliminado con exito!");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/clientes/upload")	
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Integer id){
		
		Map<String, Object> response = new HashMap<>();
		
		ModelCliente cliente = servclien.findById(id);
		
		if (!archivo.isEmpty()) {
			
			/* ESTO ESTA IMPLEMENTADO EN  IuploadfileService
			 * String nombrearchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", " ");
				Path rutaArchivo = Paths.get("uploadfotos").resolve(nombrearchivo).toAbsolutePath();			
				log.info(rutaArchivo.toString());
			*/
			String nombrearchivo= null;
			
			try {
				
				/* ESTO ESTA IMPLEMENTADO EN  IuploadfileService
				 Files.copy(archivo.getInputStream(), rutaArchivo);
				 mover el archivo del servidor a la ruta escogida
				*/
				nombrearchivo = uploadservice.guardarimagen(archivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.put("mensaje", "Error al subir la imagen del cliente en la Base de Datos");//+ nombrearchivo
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombrefotoanterior = cliente.getFoto();			
			uploadservice.eliminar(nombrefotoanterior);
			
			/* ESTO ESTA IMPLEMENTADO EN  IuploadfileService
			 * if (nombrefotoanterior != null && nombrefotoanterior.length() >0) {
				Path rutafotoanterior = Paths.get("uploadfotos").resolve(nombrefotoanterior).toAbsolutePath();
				//se obtiene la ruta completa y el archivo
				File archivofotoanterior = rutafotoanterior.toFile();
				//convertir a un objeto de tipo File
				if ( archivofotoanterior.exists() && archivofotoanterior.canRead()) {
					//ahora validamos que este archivo exista se puede leer y se elimine
					archivofotoanterior.delete();
				}				
			}
			*/
			
			cliente.setFoto(nombrearchivo);
			//setfoto para poder mostrarlo en angular
			
			servclien.save(cliente);
			//se actualiza el cliente
			
			response.put("cliente", cliente);//se esta retornando un objeto cliente en un JSON dentro del atributo cliente 
			response.put("mensaje", "Has subido correctamente la imagen: "+ nombrearchivo);
		}
		/* Detalles: 1. la imagen no puede pesar mas de 1mb,
		 * 2. renombrar los archivos si el cliente sube una imagen con el mismo nombre
		 */		
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);		
	}
	
	@GetMapping("/upload/img/{nombreFoto:.+}")//{nombreFoto:.+} indica que va a tener una extension
	public ResponseEntity<Resource> verfoto(@PathVariable String nombreFoto){
		
		/* ESTO ESTA IMPLEMENTADO EN  IuploadfileService
		 * Path rutaArchivo = Paths.get("uploadfotos").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());//por el constructor vamos a pasar la ruta convertida a una URI
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!recurso.exists() && !recurso.isReadable()){
			rutaArchivo = Paths.get("src/main/resources/static/img").resolve("nouser.png").toAbsolutePath();
			
			try {
				recurso = new UrlResource(rutaArchivo.toUri());//por el constructor vamos a pasar la ruta convertida a una URI
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			//throw new RuntimeException("No se pudo carga la imagen: "+ nombreFoto);
			log.error("No se pudo carga la imagen: "+ nombreFoto);
		}
		*/
		
		Resource recurso = null;		
		try {
			recurso = uploadservice.cargarimagen(nombreFoto);
		} catch (MalformedURLException e) {	e.printStackTrace();}
		
		HttpHeaders cabecera = new HttpHeaders();//se utiliza el signo \ para escapar de las comillas
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename()+"\"" );//como valor se agrega el attackmen
		//las cabeceras se implementa para que el recurso se pueda descargar
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);		
	}
	 
	/***********CURSO************/
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/clientes/region")
	public List<Modelregion> listarRegion(){
		return servclien.listarRegion();
	}
	
	
	
	

}
