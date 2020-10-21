package com.birnbaua.easyshop.shop.order.id;

import java.io.Serializable;

public class ItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -460361702278540220L;

	private String shop;
	
	private String name;
	
	public ItemId() {
		
	}
	
	public ItemId(String shop, String name) {
		this.shop = shop;
		this.name = name;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ItemId) {
			ItemId itemId = (ItemId) obj;
			if(itemId.shop.equals(this.shop) && itemId.name.equals(this.name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.shop.hashCode() * this.name.hashCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
