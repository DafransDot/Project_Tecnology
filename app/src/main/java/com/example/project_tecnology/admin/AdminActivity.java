package com.example.project_tecnology.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.project_tecnology.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

    binding.btnTambahBarang.setOnClickListener(v ->{
        Intent intent = new Intent(this, addBarangAdmin.class);
        startActivity(intent);
    });


    }
}