package com.oscar.ecommerce.service;

import java.util.Optional;

import com.oscar.ecommerce.model.Usuario;

public interface IUsuarioService {

	
	//Definimos metodos
	Optional<Usuario> findById(Integer id);
}
