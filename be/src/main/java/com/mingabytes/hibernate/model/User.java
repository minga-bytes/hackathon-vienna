package com.mingabytes.hibernate.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "User")
public class User { 
	


	public User(Integer id, Timestamp createdAt, String username) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.username = username;
	}

	
	@Id
	private Integer id;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "username")
	private String username;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String get() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}