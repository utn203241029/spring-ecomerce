package com.oscar.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscar.ecommerce.model.Producto;
import com.oscar.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	//para poder acceder a los productos
	//Autowired para inyectar a esta clase
	@Autowired
	private ProductoService productoservice;
	
	@GetMapping("")
	public String home(Model model) {//Se le agrega el parameto model para mandar los productos a la vista home
		
		//Que nos retorna todos los productos de la base datos
		List<Producto>productos = productoservice.findAll();
		model.addAttribute("productos", productos);
		
		return "administrador/home";
	}

}
