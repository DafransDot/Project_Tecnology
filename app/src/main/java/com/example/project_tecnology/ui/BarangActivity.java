package com.example.project_tecnology.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.barangAdapter;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityBarangBinding;
import com.example.project_tecnology.model.barang.BarangResponse;
import com.example.project_tecnology.model.barang.DataBarang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {
    private ActivityBarangBinding binding;
    private ApiInterface apiInterface;
    private barangAdapter adapter;
    private List<DataBarang> dataBarangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityBarangBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        dataBarangs = new ArrayList<>();
        adapter = new barangAdapter(this, dataBarangs);
        binding.recyclerViewBarang.setAdapter(adapter);
        binding.recyclerViewBarang.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recyclerViewBarang.setLayoutManager(layoutManager);
        binding.recyclerViewBarang.setAdapter(adapter);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        int id = getIntent().getIntExtra("id",0);
        listBarang(id); // Example category ID
        Log.d("list barang", "onCreate: ");
    }

    private void listBarang(int kategori_id) {
        Call<BarangResponse> call = apiInterface.getBarangByCategory(kategori_id);
        call.enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BarangResponse responseData = response.body();
                    if (responseData.isStatus()) {
                        dataBarangs.clear();
                        dataBarangs.addAll(responseData.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(BarangActivity.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BarangActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(BarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("BarangActivity", "onFailure: ", t);
            }
        });
    }
}
