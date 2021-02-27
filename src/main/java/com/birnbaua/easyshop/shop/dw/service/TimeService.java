package com.birnbaua.easyshop.shop.dw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.service.JpaService;
import com.birnbaua.easyshop.shop.dw.dimension.Time;
import com.birnbaua.easyshop.shop.dw.repository.TimeRepository;

@Service
public class TimeService extends JpaService<Time,Long> {
	
	@Autowired
	private TimeRepository tr;
	
	@Override
	public JpaRepository<Time, Long> getRepository() {
		return tr;
	}
	
}
