package com.oscar.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscar.ecommerce.model.Orden;
import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.service.IOrdenService;
import com.oscar.ecommerce.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")//Terminacionn de url
public class UsuarioController {
	//Logger proporciona una variedad de métodos con los que se pueden registrar los datos.
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	//Para accerder al tema de operacion crud es el archivo UsuarioServiceImpl
	 @Autowired 
	 private IUsuarioService usuarioService;
	 
	 @Autowired
	 private IOrdenService ordenService;
	 
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
	 
	 //Vista del usuario
	 @GetMapping("/login")
	 public String login() {
		 
		 
		 return "usuario/login";
	 }
	 
	 @PostMapping("/acceder")                //Este objeto se mantiene durante toda la sesion
	  public String acceder(Usuario usuario, HttpSession session) {
		 logger.info("Accesos : {}", usuario); 
		                                                //Se obtendra un usuario que tenga un email
		 Optional<Usuario> user= usuarioService.findByEmail(usuario.getEmail());
		 
		 //logger.info("Usuario db: {} ", user.get());
		 
		 //Si un usuario esta presente 
		 if(user.isPresent()) { //setAttribute nos permite poner dos parametros 1 Nombre 2 Valor
			 //Aqui se guardara el id del usuario para poder utilizarlo en el resto de lugares que se ocupara en la aplicacion
			 session.setAttribute("idusuario", user.get().getId());
		     
			 //Si el tipo es igual a ADMIN me lance a un lugar osea al administrador
			 if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			
				//Si no es ADMIN me direccione hacia la pagina home
			 }else {
				return "redirect:/";
			}
			 
	     //Si no esta presente (existe) el usuario 
		 }else {
			 logger.info("Usuario no existe");
		 }
		 
		  return "redirect:/";
	  }
	 @GetMapping("/compras")
	 public String obtenerCompras(Model model, HttpSession session) {
		 model.addAttribute("sesion", session.getAttribute("idusuario"));
		Usuario usuario= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		 List<Orden> ordenes =  ordenService.findByUsuario(usuario);
		 
		 model.addAttribute("ordenes", ordenes);
		 return "usuario/compras";
	 }

}
