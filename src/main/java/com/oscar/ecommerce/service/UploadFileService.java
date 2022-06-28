package com.oscar.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//ClASE DE SERVICIO de carga de archivos
//Aqui contendra subir imagenes o eliminar img que este en el proyecto
@Service
public class UploadFileService {
	//Esta varible tendra la ubicacion en el proyecto
	//donde se cargaran la img y se almacenara el nombre de la img
	//le colocamos images porque asi se llama la carpeta
	
	private String folder="images//";
	
	public String saveImage(MultipartFile file) throws IOException {
		//Si hay una imagen va aqui en este if
		if (!file.isEmpty()) {
			byte[]bytes=file.getBytes();
			Path path =Paths.get(folder+file.getOriginalFilename());
		    //path es la ruta y la imagen a sido transformado por la variable byts
			//para poderlo enviar al servidor
			Files.write(path, bytes);
			//Se retorna para poner solo el nombre y la extension guardarla
			return file.getOriginalFilename();
		}
		//En caso de que el usuario no suba una imagen 
		//Se pondra en ese directorio una img por defecto cuando el usuario no agrego una img
		return "defualt.jpg";
	}
	//Aqui se eliminara la imagen cuando se elimine el pregunto
	//va a resibir como parametro el nombre de la img
	public void deleteImage(String nombre) {
		
		String ruta="images//";
		//Como constructor Se colocara la ruta y el nombre que viene por parametro
		File file= new File(ruta+nombre);
		//Con este ya se eliminara la parte de la imagen cuando eliminemos un producto
		file.delete();
	}

}
