package com.oscar.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.ecommerce.model.Usuario;
import com.oscar.ecommerce.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImp implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> findById(Integer id) {
		// Aqui trae a los datos del usuario
		return usuarioRepository.findById(id);
	}
	
	
	
	
	
}
