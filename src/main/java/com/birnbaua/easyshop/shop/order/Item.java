package com.birnbaua.easyshop.shop.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.id.ItemId;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"shop_id","name"})})
@IdClass(ItemId.class)
public class Item extends BaseEntity {
	
	@Id
	@JsonIgnore
    @ManyToOne(targetEntity = Shop.class)
	@JoinColumn(name = "shop_id", referencedColumnName = "name")
    private Shop shop;
	
	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "`desc`", nullable = true)
	private String desc;
	
	@Min(0)
	@Column(name = "price")
	private Double price = 0.0;
	
	@Min(1)
	@Column(name = "`max`")
	private Integer maxAmount;
	
	@Column(name = "is_available")
	private Boolean isAvaliable;
	
	public Item() {
		
	}
	
	public Item(String name) {
		this.name = name;
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getMaxAmount() {
		return maxAmount;
	}
	
	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Boolean getIsAvaliable() {
		return isAvaliable;
	}

	public void setIsAvaliable(Boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

	@Override
	public boolean equals(Object obj) {
		return new ItemId(this.shop.getName(),this.name).equals(obj);
	}
}
