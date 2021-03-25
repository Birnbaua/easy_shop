package com.birnbaua.easyshop.shop;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.service.MetaService;
import com.birnbaua.easyshop.shop.config.ShopConfig;
import com.birnbaua.easyshop.shop.item.Item;
import com.birnbaua.easyshop.shop.order.BaseEntity;
import com.birnbaua.easyshop.shop.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class Shop extends BaseEntity<Shop,String> {
	
	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "`desc`", length = 1024)
	private String desc;
	
	@Column(name = "title", length = 64)
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "shop_owner", nullable = true)
	private User owner;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="`config`")
	private ShopConfig config;
	
	public Shop() {
		
	}
	
	public Shop(String shop) {
		this.name = shop;
	}
	
	@PostPersist
	@PostUpdate
	private void storeChangesToDw() {
		//TODO save to dw
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ShopConfig getConfig() {
		return config;
	}

	public void setConfig(ShopConfig config) {
		this.config = config;
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
