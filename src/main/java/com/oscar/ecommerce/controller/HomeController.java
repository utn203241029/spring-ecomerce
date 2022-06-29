package com.oscar.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscar.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")//aqui va aputar a la raiz del proyecto
public class HomeController {
	
	//private se refiere a nivel de clase
	@Autowired
	private ProductoService productoService ;
	
	@GetMapping("")
	public String home(Model model) {
		
		//findAll() trae todos los productos y nos lo pone en la variable "productos"
		model.addAttribute("productos", productoService.findAll());
		
		return"usuario/home";
	}
	

}
