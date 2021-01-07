package com.birnbaua.easyshop.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.birnbaua.easyshop.shop.order.id.ShopTableId;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "`table`", uniqueConstraints = {@UniqueConstraint(columnNames = {"shop_id", "table_name"})})
@IdClass(ShopTableId.class)
public class ShopTable {
	
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id", referencedColumnName = "name")
	private Shop shop;
	
	@Id
	@Column(name = "table_nr")
	private Integer nr;
	
	@Column(name = "table_name")
	private String name;
	
	@Column(name = "isAvaliable")
	private Boolean isAvaliable = true;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsAvaliable() {
		return isAvaliable;
	}

	public void setIsAvaliable(Boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	
}
