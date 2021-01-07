package com.birnbaua.easyshop.shop.order.id;

import java.io.Serializable;

public class OrderPosId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7589048016311795195L;
	
	private OrderId order;
	private Integer nr;
	
	
//	public String getShop() {
//		return shop;
//	}
//	public void setShop(String shop) {
//		this.shop = shop;
//	}
	public OrderId getOrder() {
		return order;
	}
	public void setOrder(OrderId order) {
		this.order = order;
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
		if(obj instanceof OrderPosId) {
			OrderPosId id = (OrderPosId) obj;
			if(id.order.equals(this.order) && id.nr.equals(this.nr)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (order.hashCode()) % nr.hashCode();
	}
	
}
