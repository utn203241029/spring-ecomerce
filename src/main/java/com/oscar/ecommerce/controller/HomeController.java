package com.oscar.ecommerce.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

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

import com.oscar.ecommerce.model.DetalleOrden;
import com.oscar.ecommerce.model.Orden;
import com.oscar.ecommerce.model.Producto;
import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.service.IDetalleOrdenService;
import com.oscar.ecommerce.service.IOrdenService;
import com.oscar.ecommerce.service.IUsuarioService;
import com.oscar.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")//aqui va aputar a la raiz del proyecto
public class HomeController {
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	//private se refiere a nivel de clase
	@Autowired
	private ProductoService productoService ;
	
	@Autowired
	private IUsuarioService usuarioService; 
	
	@Autowired
	private IOrdenService ordenService;
	
	@Autowired
	private IDetalleOrdenService detalleOrdenService;
	
	//Esto es para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	
	//Detalles y Orden son globales en esta clase
	//Datos de la orden
	Orden orden = new Orden();
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		log.info("Sesion del Usuario: {}", session.getAttribute("idusuario"));
		
		//findAll() trae todos los productos y nos lo pone en la variable "productos"
		model.addAttribute("productos", productoService.findAll());
		
		//Session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return"usuario/home";
	}
	
	//El siguiente metodo tiene como funcion es llevarnos ver producto
               //Le colocamos id para buscarlo por su id del producto	
	@GetMapping("productohome/{id}")                     //Model nos permite llevar informacion hacia la vista
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("id producto enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto>productoOptional = productoService.get(id);
		producto= productoOptional.get();
		
		//Ahora se envia hacia la vista
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}
	//carrito
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		//Esta variabe sirve para cuando un usario compre otro producto se inicie desde 0
		double sumaTotal=0;
		
		//ahora queda buscar nuestro producto
		Optional<Producto> optionalProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionalProducto.get());
		log.info("Cantidad: {}", cantidad);
		
		producto=optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto);
		
		//Validar que producto no se añada dos veces 
		//Ejemplo aqui viene el id con 5
		Integer idProducto=producto.getId();
		                  //En lasta detalles ya un id con 5 y de tal forma no se ingrese productos con el mismo id
		                                    //es como un for
		boolean ingresado= detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		//Si esto no es true lo añade a detallesOrden
		//Y si es true no lo añade
		if (!ingresado) {
			detalles.add(detalleOrden);
		}
		
		//suma todos los totales que tenemos en la lista
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	//Quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		
		//Lista nueva de productos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden: detalles) {
			//lo que va hacer es que si encuentra un id que ya este en detalles no lo va añadir
			//Ejmplo tenemos 3 productos en el carrito y eliminamos uno aqui se añadira el 1 y 2
			
			if(detalleOrden.getProducto().getId()!=id) {
				ordenesNueva.add(detalleOrden);
				
			}
		}
		
		//poner la nueva lista con productos restantes 
		detalles = ordenesNueva;
		
		//Ahora se recalcula porque ya se elimino un producto
		double sumaTotal=0;
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		//Session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/carrito";
	}
	
	@GetMapping("/order")
	public String order(Model model, HttpSession session) {
		                                                                                           //Se le tiene que colocar un to String porque essto session.getAttribute("idusuario") es como un obejto
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		return "usuario/resumenorden";
	}
	
	//Guardan Orden
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		
		//Nos va a permitir obtener la fecha acutual y la que se creo
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		
		//usuario 
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		orden.setUsuario(usuario);
		ordenService.save(orden);
		
		//Guardar detalles
		for(DetalleOrden dt:detalles) {
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		
		//limpiar para que si el usuario quiera seguir comprando
		orden = new Orden();
		detalles.clear();
		
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		log.info("Nombre del producto: {}", nombre);
		                                          //findAll Se atrae todos los productos
		                            //Obtiene todos los productos se le pasa un filter, se le pasa el predicado (p)
		//el predicado es lo que queremos hacer y atra ves de la fecha trae el nombre de producto se la pasa la secuencia de caracteres y  sirve que uando se escriba que algun nombre parecido de un producto lo muestre
		List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("productos", productos);
		
		return "usuario/home";
	}

}
