package com.oscar.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.ecommerce.model.Orden;
import com.oscar.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService{
   
	@Autowired
	private IOrdenRepository OrdenRepository;
	
	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return OrdenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		// Con esto optenemos todas las ordenes
		return OrdenRepository.findAll();
	}
	
	//GENERAR NUMERO ORDEN
	public String generarNumeroOrden() {
		int numero=0;
		String numeroConcatenado="";
		
		//findAll() para que nos retorne todas las ordenes
		List<Orden> ordenes= findAll();
		
		//Aqui colocaremos la lista ordenes porque es una cadena string y la pasaremos a numeros
		List<Integer> numeros=new ArrayList<Integer>();
		
		
		ordenes.stream().forEach(p -> numeros.add(Integer.parseInt(p.getNumero())));
		
		if (ordenes.isEmpty()) {
			numero=1;
		}else {
			numero=numeros.stream().max(Integer::compare).get();
		    numero++;
		}
		
		if (numero<10) {//000000001
			numeroConcatenado="000000000"+String.valueOf(numero);
			
		}else if(numero<100){//000000100
			numeroConcatenado="00000000"+String.valueOf(numero);
			
		}else if(numero<1000){//000001000
			numeroConcatenado="0000000"+String.valueOf(numero);
			
		}else if(numero<10000){//000010000
			numeroConcatenado="0000000"+String.valueOf(numero);
			
		}
		
		return numeroConcatenado;
	}

}
