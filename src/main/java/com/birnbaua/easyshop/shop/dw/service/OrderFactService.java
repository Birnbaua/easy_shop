package com.birnbaua.easyshop.shop.dw.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.service.JpaService;
import com.birnbaua.easyshop.shop.dw.fact.OrderFact;
import com.birnbaua.easyshop.shop.dw.repository.OrderFactRepository;

public class OrderFactService extends JpaService<OrderFact,Long> {
	
	@Autowired
	private OrderFactRepository ofr;

	@Override
	public JpaRepository<OrderFact, Long> getRepository() {
		return ofr;
	}

}
