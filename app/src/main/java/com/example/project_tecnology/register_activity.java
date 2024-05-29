package com.example.project_tecnology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityRegisterBinding;
import com.example.project_tecnology.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register_activity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    String Username, Password, Name;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(view->{
            Username = binding.registerUsername.getText().toString();
            Password = binding.registerPassword.getText().toString();
            Name = binding.registerNama.getText().toString();
            register(Username,Password,Name);

        });

        binding.registerToLogin.setOnClickListener(view->{
            Intent intent = new Intent(this, Login_activity.class);
            startActivity(intent);
        });
    }

    private void register(String username, String password, String name) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(username, password, name);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(register_activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register_activity.this, Login_activity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(register_activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(register_activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, Login_activity.class);
        startActivity(intent);
    }
}