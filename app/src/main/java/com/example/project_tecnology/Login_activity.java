package com.example.project_tecnology;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityLoginBinding;
import com.example.project_tecnology.model.login.Login;
import com.example.project_tecnology.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_activity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    String Username, Password;
    ApiInterface apiInterface;
    private SessionManager sessionManager;
    public Integer id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnlogin.setOnClickListener(view->{
            Username = binding.etloginUsername.getText().toString();
            Password = binding.etloginPassword.getText().toString();
            login(Username, Password);
        });

        binding.btnloginToRegister.setOnClickListener(view->{
            Intent intent = new Intent(this, register_activity.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(username, password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    sessionManager = new SessionManager(Login_activity.this);
                    LoginData loginData = response.body().getData();
                    id = loginData.getUserId();
                    sessionManager.createLoginSession(loginData);
                    Log.d("Login Activity", "Login successfully"+id);
                    Toast.makeText(Login_activity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_activity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Log.d("Login Activity", "Login Failed");
                    Toast.makeText(Login_activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("Login Activity", "Failed fetch data " + t.getMessage());
                Toast.makeText(Login_activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}