package com.birnbaua.easyshop.shop.dw.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.shop.dw.fact.SalesFact;

public interface SalesFactRepository extends JpaRepository<SalesFact,Long> {

}
