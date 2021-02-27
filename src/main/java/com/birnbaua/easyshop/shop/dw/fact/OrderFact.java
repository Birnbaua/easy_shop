package com.birnbaua.easyshop.shop.dw.fact;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.birnbaua.easyshop.shop.dw.dimension.Product;
import com.birnbaua.easyshop.shop.dw.dimension.Store;
import com.birnbaua.easyshop.shop.dw.dimension.Time;

@Entity
@Table(name = "`order_fact`")
public class OrderFact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`sk`")
	private Long sk;
	
	@ManyToOne
	@JoinColumn(name = "`time`")
	private Time time;
	
	@ManyToOne
	@JoinColumn(name = "`product`")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "`store`")
	private Store store;
	
	//Aggregated costs of sold items
	@Column(name = "`costs`")
	private BigDecimal costs;
	
	//aggregated sales of sold items
	@Column(name = "`sales`")
	private BigDecimal sales;
	
	//number of different items per order
	@Column(name = "unit_count")
	private Integer unitCount;
	
	//number of items of this order
	@Column(name = "unit_sales")
	private Integer unitSales;

	public Long getSk() {
		return sk;
	}

	public void setSk(Long sk) {
		this.sk = sk;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public Integer getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(Integer unitCount) {
		this.unitCount = unitCount;
	}

	public Integer getUnitSales() {
		return unitSales;
	}

	public void setUnitSales(Integer unitSales) {
		this.unitSales = unitSales;
	}
	
	
}
