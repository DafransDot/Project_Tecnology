package com.example.project_tecnology.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tecnology.MainActivity;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends AppCompatActivity {
    private Button buttonBackToProfile, buttonSubmitUpdate;
    private ImageView imageViewUpdateProfile;
    private TextView uploadImages;
    private EditText editTextUpdateUsername, editTextUpdateEmail, editTextUpdatePassword, editTextUpdateNoPhone;
    private ApiInterface apiInterface;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        buttonBackToProfile = findViewById(R.id.buttonBackToProfil);
        buttonSubmitUpdate = findViewById(R.id.buttonSubmitUpdate);
        imageViewUpdateProfile = findViewById(R.id.imagesViewUpdateProfil);
        uploadImages = findViewById(R.id.UploadImages);
        editTextUpdateUsername = findViewById(R.id.EditTextUpdateUsername);
        editTextUpdateEmail = findViewById(R.id.EditTextUpdateEmail);
        editTextUpdatePassword = findViewById(R.id.EditTextUpdatePassword);
        editTextUpdateNoPhone = findViewById(R.id.EditTextUpdateNoPhone);

        loadProfile();

        buttonBackToProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateProfilActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buttonSubmitUpdate.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String username = editTextUpdateUsername.getText().toString().isEmpty() ? sharedPreferences.getString("username", "") : editTextUpdateUsername.getText().toString();
            String name = editTextUpdateEmail.getText().toString().isEmpty() ? sharedPreferences.getString("name", "") : editTextUpdateEmail.getText().toString();
            String password = editTextUpdatePassword.getText().toString().isEmpty() ? sharedPreferences.getString("password", "") : editTextUpdatePassword.getText().toString();
            String phone = editTextUpdateNoPhone.getText().toString().isEmpty() ? sharedPreferences.getString("phone", "") : editTextUpdateNoPhone.getText().toString();
            String profilePhotoPath = uploadImages.getText().toString().isEmpty() ? null : uploadImages.getText().toString();

            updateProfile(username, name, password, phone, profilePhotoPath);
        });

        uploadImages.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,1);
        });


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void loadProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        String phone = sharedPreferences.getString("phone", "");

        editTextUpdateUsername.setText(username);
        editTextUpdateEmail.setText(name);
        editTextUpdatePassword.setText(password);
        editTextUpdateNoPhone.setText(phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null){
            uri = data.getData();
            imageViewUpdateProfile.setImageURI(uri);

        }
    }

    private void updateProfile(String username, String name, String password, String phone, String profilePhotoPath) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user", null);

        Log.d("UpdateProfilActivity", "User ID before update: " + userId);
        if (userId != null) { // Check if userId is not null
            int id;
            try {
                id = Integer.parseInt(userId); // Parse userId to Integer
            } catch (NumberFormatException e) {
                Toast.makeText(this, "User ID is not a valid integer", Toast.LENGTH_SHORT).show();
                return;
            }



            apiInterface.updateProfile(id, username, name, password, phone, profilePhotoPath).enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("name", name);
                        editor.putString("password", password);
                        editor.putString("phone", phone);
                        editor.apply();

                        loadProfile();
                        Intent intent = new Intent(UpdateProfilActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(UpdateProfilActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProfilActivity.this, "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    Toast.makeText(UpdateProfilActivity.this, "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }
}
