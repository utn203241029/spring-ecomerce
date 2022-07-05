package com.oscar.ecommerce.service;

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

}
