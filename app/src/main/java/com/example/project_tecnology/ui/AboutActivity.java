package com.example.project_tecnology.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.project_tecnology.MainActivity;
import com.example.project_tecnology.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView IVBackProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        IVBackProfil = findViewById(R.id.IVBackProfil);


        IVBackProfil.setOnClickListener(v -> {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}