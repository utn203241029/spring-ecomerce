package com.oscar.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.ecommerce.model.Usuario;

                                            //JpaRepository por defecto obtienen un registro por medio del ID 
@Repository                 
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	
	Optional<Usuario> findByEmail(String email);
}
