package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.ShopTableRepository;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

@Service
public class ShopTableService extends JpaService<ShopTable,ShopTableId>{
	
	@Autowired
	private ShopTableRepository sr;
	
	public List<ShopTable> findTables(String shop) {
		return sr.findTables(shop);
	}
	
	public List<ShopTable> findAvaliableTables(String shop) {
		return sr.findAvaliableTables(shop);
	}
	
	@Override
	public JpaRepository<ShopTable, ShopTableId> getRepository() {
		return sr;
	}

}
