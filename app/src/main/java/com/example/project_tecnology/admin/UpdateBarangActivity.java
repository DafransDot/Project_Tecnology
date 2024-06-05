package com.example.project_tecnology.admin;

//import static com.example.project_tecnology.admin.addBarangAdmin.PICK_IMAGE_REQUEST;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.FileUtil;
import com.example.project_tecnology.MainActivity;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.ActivityUpdateBarangBinding;
import com.example.project_tecnology.model.barang.DataBarang;
import com.example.project_tecnology.ui.UpdateProfilActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBarangActivity extends AppCompatActivity {
    private ActivityUpdateBarangBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ApiInterface apiInterface;
    private Uri uri;
    private Intent intent;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUpdateBarangBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        binding.buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateBarangActivity.this, AdminActivity.class);
            startActivity(intent);
        });

        binding.buttonSubmitUpdate.setOnClickListener(v -> {
           String NamaBarang = binding.EditTextUpdateNamaBarang.getText().toString();
           String Deskripsi = binding.EditTextUpdateDeskripsi.getText().toString();
           UpdateBarang(NamaBarang,Deskripsi);
        });

        binding.UploadImages.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            binding.imagesViewUpdate.setImageURI(uri);
        }
    }

    private void UpdateBarang(String namaBarang, String deskripsi) {
        id = getIntent().getStringExtra("ID");

//        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String userId = sharedPreferences.getString("ID", null);

        Log.d("adminAdapter", "ID: " + id);
        RequestBody idr = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody namaBarangBody = RequestBody.create(MediaType.parse("text/plain"), namaBarang);
        RequestBody deskripsiBody = RequestBody.create(MediaType.parse("text/plain"), deskripsi);

        MultipartBody.Part photo_barang = null;
        if (uri != null) {
            String filePathStr = FileUtil.getPath(this, uri);
            if (filePathStr != null) {
                File file = new File(filePathStr);
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                photo_barang = MultipartBody.Part.createFormData("photo_barang", file.getName(), requestFile);
            }
        }




        apiInterface.updateBarang(idr,namaBarangBody,deskripsiBody,photo_barang).enqueue(new Callback<DataBarang>() {
            @Override
            public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UpdateBarangActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(UpdateBarangActivity.this, AdminActivity.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(UpdateBarangActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataBarang> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Eror", Toast.LENGTH_SHORT).show();
            }
        });
    }
}