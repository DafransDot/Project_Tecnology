package com.example.project_tecnology.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.FileUtil;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityAddBarangAdminBinding;
import com.example.project_tecnology.model.barang.BarangData;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addBarangAdmin extends AppCompatActivity {
    private ActivityAddBarangAdminBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;

    String nama_barang, deskripsi, PhotoBarang;
    ApiInterface apiInterface;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddBarangAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());



        binding.buttonBack.setOnClickListener( v -> {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        });

        binding.UploadImagesAddBarang.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,PICK_IMAGE_REQUEST);

        });

        binding.buttonSubmitUpdate.setOnClickListener( v->{
            nama_barang = binding.EditTextNamaBarangAdd.getText().toString();
            deskripsi = binding.EditTextDeskripsiAdd.getText().toString();

            AddBarang(nama_barang,deskripsi);
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            binding.imagesViewAddBarang.setImageURI(uri);
        }
    }

    private void AddBarang(String nama_barang, String deskripsi) {
        RequestBody nama_barangBody = RequestBody.create(MediaType.parse("text/plain"),nama_barang);
        RequestBody deskripsiBody = RequestBody.create(MediaType.parse("text/plain"),deskripsi);

        MultipartBody.Part photo_barang = null;
        if (uri != null) {
            String filePathStr = FileUtil.getPath(this, uri);
            if (filePathStr != null) {
                File file = new File(filePathStr);
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                photo_barang = MultipartBody.Part.createFormData("photo_barang", file.getName(), requestFile);
            }
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<BarangData> call = apiInterface.BarangResponse(nama_barangBody, deskripsiBody,photo_barang);
        call.enqueue(new Callback<BarangData>() {
            @Override
            public void onResponse(Call<BarangData> call, Response<BarangData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BarangData responseData = response.body();
                    if (responseData.isStatus()) {
                        Toast.makeText(addBarangAdmin.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(addBarangAdmin.this, AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(addBarangAdmin.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(addBarangAdmin.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        Log.d("RetrofitError", "Error body: " + errorBody);
                    } catch (Exception e) {
                        Toast.makeText(addBarangAdmin.this, "Response parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("RetrofitError", "Parsing error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BarangData> call, Throwable t) {
                Toast.makeText(addBarangAdmin.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("RetrofitError", "Failure: " + t.getLocalizedMessage());
            }
        });
    }
}