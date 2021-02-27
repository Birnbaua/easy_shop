package com.birnbaua.easyshop.shop.order.id;

import java.io.Serializable;

public class OrderId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ShopTableId table;
	
	private Long nr;
	
	public OrderId() {
		
	}
	
	public OrderId(ShopTableId table, Long nr) {
		this.table = table;
		this.nr = nr;
	}

	public Long getNr() {
		return nr;
	}

	public void setNr(Long nr) {
		this.nr = nr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ShopTableId getTable() {
		return table;
	}

	public void setTable(ShopTableId table) {
		this.table = table;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OrderId) {
			OrderId orderId = (OrderId) obj;
			if(orderId.nr.equals(this.nr) && orderId.table.equals(this.table)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.nr.intValue() * this.table.hashCode();
	}



}
