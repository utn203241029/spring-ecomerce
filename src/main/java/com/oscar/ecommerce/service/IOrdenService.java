package com.oscar.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.oscar.ecommerce.model.Orden;
import com.oscar.ecommerce.model.Usuario;

public interface IOrdenService {
	
	//Va a permitir una lista de ordenes
	List<Orden> findAll();
	Optional<Orden> findById(Integer id);
	Orden save(Orden orden);
	String generarNumeroOrden();
	
	List<Orden> findByUsuario(Usuario usuario);
	
 
}
