package com.oscar.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")//Terminacionn de url
public class UsuarioController {
	//Logger proporciona una variedad de métodos con los que se pueden registrar los datos.
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	//Para accerder al tema de operacion crud es el archivo UsuarioServiceImpl
	 @Autowired 
	 private IUsuarioService usuarioService;
	 
	 // /usuario/registro
	 @GetMapping("/registro")
	 public String create() {
		 
		 return "usuario/registro";
	 }
	 //PosMapping ya que toda la informacion que se enviara esta en un formulario
	 @PostMapping("/save")
	 public String save(Usuario usuario) {
		 
		 logger.info("Usuario registro: {}", usuario);
		 usuario.setTipo("USER");
		 usuarioService.save(usuario);
		 
		 return "redirect:/";
	 }
	 
	 

}
