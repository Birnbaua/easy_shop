package com.birnbaua.easyshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.ShopConfigRepository;
import com.birnbaua.easyshop.shop.config.ShopConfig;

@Service
public class ShopConfigService extends JpaService<ShopConfig,Integer> {
	
	@Autowired
	private ShopConfigRepository scr;
	
	@Override
	public JpaRepository<ShopConfig, Integer> getRepository() {
		return scr;
	}
	
}
