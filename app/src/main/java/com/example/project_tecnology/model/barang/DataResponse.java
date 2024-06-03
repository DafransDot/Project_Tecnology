package com.example.project_tecnology.model.barang;

import com.google.gson.annotations.SerializedName;

public class DataResponse {

	@SerializedName("photo_barang")
	private Object photoBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("deskripsi")
	private String deskripsi;

	public void setPhotoBarang(Object photoBarang){
		this.photoBarang = photoBarang;
	}

	public Object getPhotoBarang(){
		return photoBarang;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}
}