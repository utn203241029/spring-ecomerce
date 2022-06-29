package com.oscar.ecommerce.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oscar.ecommerce.model.Producto;
import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.service.ProductoService;
import com.oscar.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	//
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	//Autowired esto srive para inyectar a la clase productoController
	@Autowired
	private UploadFileService upload;
	
	
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
	//El parametro producto si tiene todos sus campos del formulario de obj producto
	//La imagen no la tiene asi que se le agrega una notacion @RequestParam y lo traera desde el atributo img, este atributo esta en create.html
	
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		//La apertura de llaves es una especie de format, el cual le decimos que va a venir una variable o un objeto
		//Con lo ya dicho de arriba tambien me sirve para verlo en consola como si fue se un system.out.....
		LOGGER.info("Este es el objeto producto {}", producto);
		Usuario u= new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		
		//imagen
		//Esta validacion es cuando se crea un producto por primera vez
		if (producto.getId()==null) {
			String nombreImagen=upload.saveImage(file);
			//Con esto ya se guarda en el campo images
			//Aqui lo que se esta pasando es al obj producto que este ya lo guarda todos los campos que vienen para ese obj
			producto.setImagen(nombreImagen);
		}else {
			
		}
		
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
	public String update(Producto producto,  @RequestParam("img") MultipartFile file) throws IOException {
		Producto p=new Producto();
		p=productoService.get(producto.getId()).get();
		
		//Esta condicion es cuando editamos el producto pero no se cambia la imagen
		if (file.isEmpty()) {
			
			    producto.setImagen(p.getImagen());
		}else {
			//Cuando se edita la imagen siempre y cuando no sea la de por defecto		
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}
		String nombreImagen = upload.saveImage(file);
		producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p= new Producto();
		p=productoService.get(id).get();
		
		//Se elimira la img cuando la misma no sea por defecto
		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}
		
		productoService.delete(id);
		return "redirect:/productos";
	}

}
