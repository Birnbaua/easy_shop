package com.birnbaua.easyshop.shop.dw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.service.JpaService;
import com.birnbaua.easyshop.shop.dw.dimension.Store;
import com.birnbaua.easyshop.shop.dw.repository.StoreRepository;

@Service
public class StoreService extends JpaService<Store,Integer> {
	
	@Autowired
	private StoreRepository sr;

	@Override
	public JpaRepository<Store, Integer> getRepository() {
		return sr;
	}
	
}
