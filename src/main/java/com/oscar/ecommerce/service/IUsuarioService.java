package com.oscar.ecommerce.service;

import java.util.Optional;

import com.oscar.ecommerce.model.Usuario;

public interface IUsuarioService {

	
	//Definimos metodos
	Optional<Usuario> findById(Integer id);
	//Guardar
	Usuario save (Usuario usuario);
	
	Optional<Usuario> findByEmail (String email);
	
}
