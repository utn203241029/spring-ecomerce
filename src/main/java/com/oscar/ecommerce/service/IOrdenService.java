package com.oscar.ecommerce.service;

import java.util.List;

import com.oscar.ecommerce.model.Orden;

public interface IOrdenService {
	
	//Va a permitir una lista de ordenes
	List<Orden> findAll();
	
	Orden save(Orden orden);
	String generarNumeroOrden();
 
}
