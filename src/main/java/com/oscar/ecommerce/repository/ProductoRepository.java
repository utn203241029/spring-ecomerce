package com.oscar.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.ecommerce.model.Producto;

//Tenemos que señalarle a que tabla se debe hacer los(los select, insert etc)
//En este caso es en la tabla productos
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
   
	
}
