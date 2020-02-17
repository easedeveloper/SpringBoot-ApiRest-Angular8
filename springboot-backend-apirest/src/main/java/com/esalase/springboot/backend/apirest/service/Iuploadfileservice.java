package com.esalase.springboot.backend.apirest.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface Iuploadfileservice {
	
	public Resource cargarimagen(String nombrefoto) throws MalformedURLException;
	
	public String guardarimagen(MultipartFile archivo) throws IOException;
	
	public boolean eliminar(String nombreFoto);
	
	public Path getPath(String nombreFoto);

}
