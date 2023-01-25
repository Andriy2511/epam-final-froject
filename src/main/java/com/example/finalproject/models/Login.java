package com.example.finalproject.models;

import java.io.Serializable;

public class Login implements Serializable {
	private String username;
	private String password;
	private String userId;

	public Login() {
	}
	public Login(String username, String password, String userId) {
		this.username = username;
		this.password = password;
		this.userId = userId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Login{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}
