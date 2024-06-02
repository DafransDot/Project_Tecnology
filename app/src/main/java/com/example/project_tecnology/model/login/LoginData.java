package com.example.project_tecnology.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("username")
	private String username;
	@SerializedName("phone")
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}