package com.birnbaua.easyshop.shop.order;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.id.OrderId;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sales_order")
@IdClass(OrderId.class)
public class Order extends BaseEntity {
	
	@Id
	@JsonIgnore
    @ManyToOne(targetEntity = Shop.class)
	@JoinColumn(name = "shop_id", referencedColumnName = "name")
    private Shop shop;
	
	@Id
	@Column(name = "order_nr")
	private Long nr;
	
	
	@ManyToOne()
	@JoinColumns(value = {
		@JoinColumn(name = "shop_id_table", referencedColumnName = "shop_id"),
		@JoinColumn(name = "table_nr", referencedColumnName = "table_nr")})
	private ShopTable table;
	
	@Column(name = "is_open")
	private Boolean isOpen = true;
	
	//not calculating when fetching from db because we want to use the total price when the order goes in (price may have changed later)
	@Column(name = "total_price_at_purchasetime")
	private Double price;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumns({
		@JoinColumn(name = "shop_id", referencedColumnName = "shop_id"),
		@JoinColumn(name = "order_nr", referencedColumnName = "order_nr")})
	private List<OrderPos> orderPos = new LinkedList<>();
	
	@PreUpdate
	@PrePersist
	private void calcPrice() {
		this.price = orderPos.stream().mapToDouble(x -> x.getAmount() * x.getItem().getPrice()).sum();
		this.table.setShop(this.shop);
	}

	public ShopTable getTable() {
		return table;
	}

	public void setTable(ShopTable table) {
		this.table = table;
	}
	
	public List<OrderPos> getOrderPos() {
		return orderPos;
	}

	public void setOrderPos(List<OrderPos> orderPos) {
		this.orderPos = orderPos;
	}
	
	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Long getNr() {
		return nr;
	}

	public void setNr(Long nr) {
		this.nr = nr;
	}
}
