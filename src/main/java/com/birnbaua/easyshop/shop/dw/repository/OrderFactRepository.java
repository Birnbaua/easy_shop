package com.birnbaua.easyshop.shop.dw.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.shop.dw.fact.OrderFact;

public interface OrderFactRepository extends JpaRepository<OrderFact,Long> {

}
