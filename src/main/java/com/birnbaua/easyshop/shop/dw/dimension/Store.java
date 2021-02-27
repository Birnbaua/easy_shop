package com.birnbaua.easyshop.shop.dw.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`store_dt`")
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sk")
	private Integer sk;
	
	@Column(name = "store_name", unique = true)
	private String storeName;

	public Integer getSk() {
		return sk;
	}

	public void setSk(Integer sk) {
		this.sk = sk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
}
