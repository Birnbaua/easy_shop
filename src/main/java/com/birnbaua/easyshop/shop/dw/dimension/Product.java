package com.birnbaua.easyshop.shop.dw.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.birnbaua.easyshop.shop.item.ProductCategory;
import com.birnbaua.easyshop.shop.item.ProductFamily;

@Entity
@Table(name = "product_dt")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sk")
	private Integer sk;
	
	@Enumerated(EnumType.STRING)
	private ProductFamily family;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	
	@Column(name = "name")
	private String name;

	public Integer getSk() {
		return sk;
	}

	public void setSk(Integer sk) {
		this.sk = sk;
	}

	public ProductFamily getFamily() {
		return family;
	}

	public void setFamily(ProductFamily family) {
		this.family = family;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
