package com.example.project_tecnology.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.adminAdapter;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityAdminBinding;
import com.example.project_tecnology.model.barang.BarangResponse;
import com.example.project_tecnology.model.barang.DataBarang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private ApiInterface apiInterface;
    private adminAdapter adapter;
    private List<DataBarang> dataBarangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        dataBarangs = new ArrayList<>();
        adapter = new adminAdapter(this, dataBarangs);
        binding.recyclerViewadmin.setAdapter(adapter);
        binding.recyclerViewadmin.setLayoutManager(new LinearLayoutManager(this));

        binding.btnTambahBarang.setOnClickListener(v -> {
            Intent intent = new Intent(this, addBarangAdmin.class);
            startActivity(intent);
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        listBarang();
        Log.d("list barang", "onCreate: ");
    }

    private void listBarang() {
        apiInterface.getBarang().enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    adapter.setDataBarang(response.body().getData());
                } else {
                    Toast.makeText(AdminActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(AdminActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
