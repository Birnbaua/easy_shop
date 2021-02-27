package com.birnbaua.easyshop.shop.dw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.shop.dw.dimension.Store;

public interface StoreRepository extends JpaRepository<Store,Integer> {

}
