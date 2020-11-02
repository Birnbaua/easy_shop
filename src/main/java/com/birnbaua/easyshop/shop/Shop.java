package com.birnbaua.easyshop.shop;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "shop_owner", nullable = true)
	private User owner;
	
	@OneToMany(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "shop_id")
	private List<Item> items = new LinkedList<>();
	
	@OneToMany(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "shop_id")
	private List<Order> orders = new LinkedList<>();
	
	public Shop() {
		
	}
	
	public Shop(String name) {
		this.name = name;
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
