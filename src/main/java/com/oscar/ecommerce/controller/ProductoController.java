package com.oscar.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscar.ecommerce.model.Producto;
import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	//
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	
	@GetMapping("")
	public String show() {
		return "productos/show";
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		//La apertura de llaves es una especie de format, el cual le decimos que va a venir una variable o un objeto
		//Con lo ya dicho de arriba tambien me sirve para verlo en consola como si fue se un system.out.....
		LOGGER.info("Este es el objeto producto {}", producto);
		Usuario u= new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		productoService.save(producto);
		//redirect es una peticion, directamente a nuestro controlador productos
		//Y basicamente lo que va cargar es la vista show
		return "redirect:/productos";
	}
	
	

}
