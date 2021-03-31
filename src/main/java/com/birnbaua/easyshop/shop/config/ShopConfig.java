package com.birnbaua.easyshop.shop.config;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import com.birnbaua.easyshop.shop.order.BaseEntity;

@Entity
@Table(name = "shop_config")
public class ShopConfig extends BaseEntity<ShopConfig,Integer> implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 6135906605588295483L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Min(5)
	@Column(name = "max_tables")
	private Integer maxTables = 10;
	
	@Min(10)
	@Column(name = "max_items")
	private Integer maxItems = 15;
	
	@Column(name = "starts_at", nullable = false)
	private Timestamp startsAt = Timestamp.valueOf(LocalDateTime.now());
	
	@Column(name = "expires_at", nullable = false)
	private Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.of(2099, 12, 31, 23, 59));
	
	@Column(name = "has_logging_enabled", nullable = false)
	private Boolean hasLoggingEnabled = false;
	
	@Column(name = "max_nr_of_orders_per_table", nullable = false)
	private Integer maxNrOfOrdersPerTable = 15;
	
	@Min(0)
	@Column(name = "max_sales_value_per_order", nullable = false)
	private BigDecimal maxSalesValuePerOrder = BigDecimal.valueOf(300);
	
	public ShopConfig() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMaxTables() {
		return maxTables;
	}

	public void setMaxTables(Integer maxTables) {
		this.maxTables = maxTables;
	}

	public Integer getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}

	public Timestamp getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Timestamp startsAt) {
		this.startsAt = startsAt;
	}

	public Timestamp getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Boolean getHasLoggingEnabled() {
		return hasLoggingEnabled;
	}

	public void setHasLoggingEnabled(Boolean hasLoggingEnabled) {
		this.hasLoggingEnabled = hasLoggingEnabled;
	}

	public Integer getMaxNrOfOrdersPerTable() {
		return maxNrOfOrdersPerTable;
	}

	public void setMaxNrOfOrdersPerTable(Integer maxNrOfOrdersPerTable) {
		this.maxNrOfOrdersPerTable = maxNrOfOrdersPerTable;
	}

	public BigDecimal getMaxSalesValuePerOrder() {
		return maxSalesValuePerOrder;
	}

	public void setMaxSalesValuePerOrder(BigDecimal maxSalesValuePerOrder) {
		this.maxSalesValuePerOrder = maxSalesValuePerOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ShopConfig) {
			if(((ShopConfig)obj).id.equals(this.id)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
