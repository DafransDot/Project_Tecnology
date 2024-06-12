package com.example.project_tecnology.api;

import com.example.project_tecnology.model.ApiResponse;
import com.example.project_tecnology.model.barang.BarangResponse;
import com.example.project_tecnology.model.barang.DataBarang;
import com.example.project_tecnology.model.liveChat.liveChat;
import com.example.project_tecnology.model.login.Login;
import com.example.project_tecnology.model.login.LoginData;
import com.example.project_tecnology.model.register.Register;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call <Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call <Register> registerResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name

    );

    @FormUrlEncoded
    @POST("post_chat.php")
    Call<ResponseBody> sendChat (
            @Field("name") String name,
            @Field("chat") String chat
    );

    @GET("get_chat.php")
    Call<List<liveChat>> getChats();

    @GET("get_user.php")
    Call<ApiResponse> getUserProfile(@Query("id") int id);


    @Multipart
    @POST("update_profile.php")
    Call<LoginData> updateProfile(
            @Part("id") RequestBody id,
            @Part("username") RequestBody username,
            @Part("name") RequestBody name,
            @Part("password") RequestBody password,
            @Part("fullname") RequestBody fullname,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part profilePhoto
    );


    @Multipart
    @POST("post_barang.php")
    Call<BarangResponse> BarangResponse(
            @Part("nama_barang") RequestBody nama_barang,
            @Part("deskripsi") RequestBody deskripsi,
            @Part("kategori_id") RequestBody kategori_id,
            @Part("rating") RequestBody rating, // Menambahkan rating
            @Part("harga") RequestBody harga, // Menambahkan harga
            @Part MultipartBody.Part photo_barang
    );

    @GET("get_barang.php")
    Call<BarangResponse> getBarang(@Query("nama_barang") String namaBarang);

    // Metode untuk mendapatkan barang berdasarkan ID
    @GET("get_barang_id.php")
    Call<BarangResponse> getBarangById(@Query("id") int id);

    @Multipart
    @POST("update_barang.php")
    Call<DataBarang> updateBarang(
            @Part("id") RequestBody id,
            @Part("nama_barang") RequestBody nama_barang,
            @Part("deskripsi") RequestBody deskripsi,
            @Part("rating") RequestBody rating, // Menambahkan rating
            @Part("harga") RequestBody harga, // Menambahkan harga
            @Part MultipartBody.Part photo_barang
    );


    @FormUrlEncoded
    @POST("delete_barang.php")
    Call<DataBarang> deleteBarang(
            @Field("id") int id);

    @GET("get_barang_kategori.php")
    Call<BarangResponse> getBarangByCategory
            (@Query("kategori_id") int kategoriId);

}