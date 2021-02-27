package com.birnbaua.easyshop.shop.dw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.service.JpaService;
import com.birnbaua.easyshop.shop.dw.fact.SalesFact;
import com.birnbaua.easyshop.shop.dw.repository.SalesFactRepository;

@Service
public class SalesFactService extends JpaService<SalesFact,Long> {
	
	@Autowired
	private SalesFactRepository sfr;

	@Override
	public JpaRepository<SalesFact, Long> getRepository() {
		return sfr;
	}

}
