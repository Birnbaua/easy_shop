package com.birnbaua.easyshop.shop.order.id;

import java.io.Serializable;

public class ShopTableId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766661702738301742L;
	
	private String shop;
	private Integer nr;
	
	public ShopTableId() {
		
	}
	
	public ShopTableId(String shop, Integer table) {
		this.shop = shop;
		this.nr = table;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public Integer getNr() {
		return nr;
	}
	public void setNr(Integer nr) {
		this.nr = nr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ShopTableId) {
			ShopTableId id = (ShopTableId) obj;
			if(id.shop.equals(this.shop) && id.nr.equals(this.nr)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.shop.hashCode() * this.nr.hashCode();
	}
}
