package com.birnbaua.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.shop.order.OrderPos;

public interface OrderPosRepository extends JpaRepository<OrderPos,Long> {

}
