package com.example.project_tecnology.api;

import android.service.autofill.UserData;

import com.example.project_tecnology.model.liveChat.liveChat;
import com.example.project_tecnology.model.login.Login;
import com.example.project_tecnology.model.login.LoginData;
import com.example.project_tecnology.model.register.Register;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("login.php")
    Call<List<Login>> getUserProfile();


    @FormUrlEncoded
    @POST("update_profile.php")
    Call<LoginData> updateProfile(
            @Path("id") int id,
            @Path("username") String username,
            @Path("name") String name,
            @Path("password") String password,
            @Path("phone") String phone,
            @Path("profile_photo_path") String profilePhotoPath
    );
}