package com.birnbaua.easyshop.shop.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.birnbaua.easyshop.shop.item.Item;
import com.birnbaua.easyshop.shop.order.id.OrderPosId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "order_pos")
@IdClass(OrderPosId.class)
public class OrderPos {
	
	@Id
	@JsonIgnore
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "shop_id", referencedColumnName = "shop_id"),
		@JoinColumn(name = "order_nr", referencedColumnName = "order_nr"),
		@JoinColumn(name = "table_nr", referencedColumnName = "table_nr")})
	private Order order;
	
	@Id
	@Column(name = "order_pos_nr")
	private Integer nr;
	
	@JsonIgnoreProperties({"isAvaliable","price","maxAmount"})
	@ManyToOne(targetEntity = Item.class)
	@JoinColumns({
		@JoinColumn(name = "shop_id_item", referencedColumnName = "shop_id"),
		@JoinColumn(name = "item_name", referencedColumnName = "name")})
	private Item item;
	
	@Min(1)
	@Column(name = "amount", nullable = false)
	private Integer amount;
	
	@PrePersist
	@PreUpdate
	private void checkAmount() {
		if(item.getMaxAmount().compareTo(this.amount) < 0) {
			throw new IllegalArgumentException("The max amount for this item is " + item.getMaxAmount());
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
