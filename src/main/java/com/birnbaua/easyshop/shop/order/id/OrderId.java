package com.birnbaua.easyshop.shop.order.id;

import java.io.Serializable;

public class OrderId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String shop;
	
	private Long nr;
	
	public OrderId() {
		
	}
	
	public OrderId(String shop, Long nr) {
		this.shop = shop;
		this.nr = nr;
	}

	public Long getNr() {
		return nr;
	}

	public void setNr(Long nr) {
		this.nr = nr;
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
		if(obj instanceof OrderId) {
			OrderId orderId = (OrderId) obj;
			if(orderId.shop.equals(this.shop) && orderId.nr == this.nr) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.shop.hashCode() * this.nr.intValue();
	}

}
