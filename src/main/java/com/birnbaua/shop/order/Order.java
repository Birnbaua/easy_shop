package com.birnbaua.shop.order;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sales_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "servingTable", nullable = false)
	private String table;
	
	@Column(name = "is_open")
	private Boolean isOpen = true;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "order_id")
	private List<OrderPos> orderPos = new LinkedList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
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
}
