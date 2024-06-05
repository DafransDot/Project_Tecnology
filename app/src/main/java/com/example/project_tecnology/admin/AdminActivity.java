package com.example.project_tecnology.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.project_tecnology.Adapter.adminAdapter;
import com.example.project_tecnology.R;
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
    private EditText etCariBarang;
    private Button btnCariBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        dataBarangs = new ArrayList<>();
        adapter = new adminAdapter(this, dataBarangs);
        binding.recyclerViewadmin.setAdapter(adapter);
        binding.recyclerViewadmin.setLayoutManager(new LinearLayoutManager(this));

        // Menghubungkan EditText dan Button dengan ID yang sesuai di layout XML
        etCariBarang = findViewById(R.id.etCariBarang);
        btnCariBarang = findViewById(R.id.btnCariBarang);

        binding.btnTambahBarang.setOnClickListener(v -> {
            Intent intent = new Intent(this, addBarangAdmin.class);
            startActivity(intent);
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnCariBarang.setOnClickListener(v -> {
            String query = etCariBarang.getText().toString().trim();
            listBarang(query);
        });

        listBarang(null);
        Log.d("list barang", "onCreate: ");
    }

    private void listBarang(String namaBarang) {
        Call<BarangResponse> call;
        if (namaBarang != null && !namaBarang.isEmpty()) {
            call = apiInterface.getBarang(namaBarang);
        } else {
            call = apiInterface.getBarang("");
        }

        call.enqueue(new Callback<BarangResponse>() {
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
