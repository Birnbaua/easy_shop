package com.birnbaua.easyshop.shop.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.id.ItemId;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"shop_id","name"})})
@IdClass(ItemId.class)
public class Item {
	
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
    private Shop shop;
	
	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "`desc`", nullable = true)
	private String desc;
	
	@Column(name = "price")
	private Double price = 0.0;
	
	@Column(name = "`max`")
	private Integer maxAmount;
	
	@Column(name ="is_available")
	private Boolean isAvaliable;

	public Boolean getIsAvaliable() {
		return isAvaliable;
	}

	public void setIsAvaliable(Boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	@Override
	public boolean equals(Object obj) {
		return new ItemId(this.shop.getName(),this.name).equals(obj);
	}
}
