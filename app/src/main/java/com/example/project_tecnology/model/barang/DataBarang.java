package com.example.project_tecnology.model.barang;

import com.google.gson.annotations.SerializedName;

public class DataBarang {

	@SerializedName("photo_barang")
	private String photoBarang; // Ensure this is a String for Base64

	@SerializedName("id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("deskripsi")
	private String deskripsi;

	public void setPhotoBarang(String photoBarang){
		this.photoBarang = photoBarang;
	}

	public String getPhotoBarang(){
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
