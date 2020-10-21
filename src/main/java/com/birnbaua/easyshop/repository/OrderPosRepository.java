package com.birnbaua.easyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.shop.order.OrderPos;

public interface OrderPosRepository extends JpaRepository<OrderPos,Long> {

}
