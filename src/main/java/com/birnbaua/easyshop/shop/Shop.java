package com.birnbaua.easyshop.shop;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.birnbaua.easyshop.shop.order.Item;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class Shop {
	
	@Id
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "shop_owner", nullable = true)
	private User owner;
	
	@OneToMany()
	@JoinColumn(name = "shop_id")
	private List<Item> items = new LinkedList<>();
	
	@OneToMany()
	@JoinColumn(name = "shop_id")
	private List<Order> orders = new LinkedList<>();
	
	@OneToMany()
	@JoinColumn(name = "shop_id")
	private List<ShopTable> tables = new LinkedList<>();
	
	@Column(name = "has_limited_tables")
	private Boolean hasLimitedTables = false;
	
	public Shop() {
		
	}
	
	public Shop(String shop) {
		this.name = shop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<ShopTable> getTables() {
		return tables;
	}

	public void setTables(List<ShopTable> tables) {
		this.tables = tables;
	}

	public Boolean getHasLimitedTables() {
		return hasLimitedTables;
	}

	public void setHasLimitedTables(Boolean hasLimitedTables) {
		this.hasLimitedTables = hasLimitedTables;
	}

	@JsonIgnore
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	@JsonIgnore
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Shop) {
			if(((Shop)obj).name.equals(this.name)) {
				return true;
			}
		}
		return false;
	}

}
