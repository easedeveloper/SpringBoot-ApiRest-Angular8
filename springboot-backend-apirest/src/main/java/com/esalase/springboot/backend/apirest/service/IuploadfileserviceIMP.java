package com.esalase.springboot.backend.apirest.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IuploadfileserviceIMP implements Iuploadfileservice{
	
	private final Logger log = LoggerFactory.getLogger(IuploadfileserviceIMP.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploadfotos";

	@Override
	public Resource cargarimagen(String nombrefoto) throws MalformedURLException {
		
		Path rutaArchivo = getPath(nombrefoto);
		log.info(rutaArchivo.toString());
		
		Resource recurso = new UrlResource(rutaArchivo.toUri());//por el constructor vamos a pasar la ruta convertida a una URI
				
		if (!recurso.exists() && !recurso.isReadable()){
			rutaArchivo = Paths.get("src/main/resources/static/img").resolve("nouser.png").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());//por el constructor vamos a pasar la ruta convertida a una URI
				
			//throw new RuntimeException("No se pudo carga la imagen: "+ nombreFoto);
			log.error("No se pudo carga la imagen: "+ nombrefoto);
		}
		
		return recurso;
	}

	@Override
	public String guardarimagen(MultipartFile archivo) throws IOException {
		
		String nombrearchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", " ");
		
		Path rutaArchivo = getPath(nombrearchivo);			
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
		//mover el archivo del servidor a la ruta escogida
		
		return nombrearchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		
		if (nombreFoto != null && nombreFoto.length() >0) {
			//si el cliente que estamos eliminando tiene una imagen que esta guardado en su registro, se borra
			Path rutafotoanterior = Paths.get("uploadfotos").resolve(nombreFoto).toAbsolutePath();
			//se obtiene la ruta completa y el archivo
			File archivofotoanterior = rutafotoanterior.toFile();
			//convertir a un objeto de tipo File
			if ( archivofotoanterior.exists() && archivofotoanterior.canRead()) {
				//ahora validamos que este archivo exista se puede leer y se elimine
				archivofotoanterior.delete();
				return true;
			}				
		}
		
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
