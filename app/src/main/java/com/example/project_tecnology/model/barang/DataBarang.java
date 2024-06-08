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


	@SerializedName("kategori_id")
	private int kategoriId;

	@SerializedName("rating")
	private String rating;

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	@SerializedName("harga")
	private String harga;

	public int getKategoriId() {
		return kategoriId;
	}

	public void setKategoriId(int kategoriId) {
		this.kategoriId = kategoriId;
	}

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
