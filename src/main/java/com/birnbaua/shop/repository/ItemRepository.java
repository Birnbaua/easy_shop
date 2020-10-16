package com.birnbaua.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.shop.order.Item;

public interface ItemRepository extends JpaRepository<Item,String> {

}
