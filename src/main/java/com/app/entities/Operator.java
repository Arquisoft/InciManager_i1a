package com.app.entities;

public class Operator {

	private String idautogenerado;

	private Long id;

	private String username;
	private String password;

	public Operator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Operator() {

	}

	public String getIdautogenerado() {
		return idautogenerado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Operator [id='" + id + "', username='" + username + "', password='" + password + "']";
	}
	
}
