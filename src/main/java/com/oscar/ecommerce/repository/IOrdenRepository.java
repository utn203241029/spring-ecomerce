package com.oscar.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.ecommerce.model.Orden;
import com.oscar.ecommerce.model.Usuario;


@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{
   //Buscar todas la ordenes que ha echo un usuario 
	List<Orden> findByUsuario(Usuario usuario);
	
}
