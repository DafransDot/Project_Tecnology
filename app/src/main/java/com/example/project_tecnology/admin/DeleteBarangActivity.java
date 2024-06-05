package com.example.project_tecnology.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.barang.DataBarang;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteBarangActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_barang);

        // Inisialisasi ApiInterface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        deleteItem();
    }

    private void deleteItem() {
        id = getIntent().getStringExtra("ID");
        Log.d("adminAdapter", "ID: " + id);

        int idr = Integer.parseInt(id);
        apiInterface.deleteBarang(idr).enqueue(new Callback<DataBarang>() {
            @Override
            public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DeleteBarangActivity.this, "Item berhasil dihapus", Toast.LENGTH_SHORT).show();
                    // Mengirimkan kode balik ke aktivitas sebelumnya
                    Intent intent = new Intent(DeleteBarangActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DeleteBarangActivity.this, "Gagal menghapus item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataBarang> call, Throwable t) {
                Toast.makeText(DeleteBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
