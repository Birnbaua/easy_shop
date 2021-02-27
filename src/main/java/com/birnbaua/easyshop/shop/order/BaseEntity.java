package com.birnbaua.easyshop.shop.order;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.birnbaua.easyshop.service.MetaService;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Birnbaua
 * @version 1.0
 * @since 1.0
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<T,K> {
	
	@JsonIgnore
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt; 
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@JsonIgnore
	@CreatedBy
	@Column(name = "created_by", updatable = false, nullable = true)
	private String createdBy;
	
	@JsonIgnore
	@LastModifiedBy
	@Column(name = "last_modified_by", nullable = true)
	private String lastModifiedBy;

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
