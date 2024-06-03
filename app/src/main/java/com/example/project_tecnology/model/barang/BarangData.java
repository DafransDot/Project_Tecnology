package com.example.project_tecnology.model.barang;

import com.google.gson.annotations.SerializedName;

public class BarangData{

	@SerializedName("data")
	private DataResponse data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(DataResponse data){
		this.data = data;
	}

	public DataResponse getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}