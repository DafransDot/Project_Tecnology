package com.example.project_tecnology.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.project_tecnology.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        String nama_barang = getIntent().getStringExtra("nama_barang");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String photo_barang = getIntent().getStringExtra("photo_barang");

        if (photo_barang != null && !photo_barang.isEmpty()) {
            try {
                Bitmap bitmap = decodeBase64ToBitmap(photo_barang);
                binding.imageView.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                Log.e("DetailActivity", "Invalid Base64 image string", e);
            }
        }

        binding.tvNamaBarang.setText(nama_barang);
        binding.tvDeskripsi.setText(deskripsi);
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
