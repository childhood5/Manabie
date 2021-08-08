package com.manabie.springbootmanabie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column
	private String id;

	@Column
	@JsonIgnore
	private String password;

	@Column(name = "max_todo")
	private int maxTodo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxTodo() {
		return maxTodo;
	}

	public void setMaxTodo(int maxTodo) {
		this.maxTodo = maxTodo;
	}
}
