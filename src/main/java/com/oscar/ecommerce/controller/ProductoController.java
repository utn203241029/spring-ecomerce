package com.oscar.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String show(Model model) {
		//Con esto se envia a la varibale "productos"
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	//Metdo Guardar
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
	
	    //Metodo Acutalizar
	    //PathVariable sirve para mapear la variable que viene en la url en este caso es id
			@GetMapping("/edit/{id}")
			public String edit(@PathVariable Integer id, Model model) {
				Producto producto= new Producto();
				Optional<Producto> optionalproducto = productoService.get(id);
				producto = optionalproducto.get();
				
				LOGGER.info("Producto buscado: {}",producto);
				model.addAttribute("producto", producto);
				return "productos/edit";
	}
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productoService.delete(id);
		return "redirect:/productos";
	}

}
