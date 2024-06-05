package com.example.project_tecnology.model.barang;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarangResponse {
	@SerializedName("data")
	private List<DataBarang> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataBarang> getData() {
		return data;
	}

	public void setData(List<DataBarang> data) {
		this.data = data;
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