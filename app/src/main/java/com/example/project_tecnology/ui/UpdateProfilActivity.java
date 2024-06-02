package com.example.project_tecnology.ui;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.login.Login;
import com.example.project_tecnology.model.login.LoginData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends AppCompatActivity {

    private Button buttonBackToProfile, buttonSubmitUpdate;
    private ImageView imageViewUpdateProfile;
    private TextView uploadImages;
    private ApiInterface apiInterface;
    private EditText editTextUpdateUsername, editTextUpdateEmail, editTextUpdatePassword, editTextUpdateNoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        // Inisialisasi views
        buttonBackToProfile = findViewById(R.id.buttonBackToProfil);
        buttonSubmitUpdate = findViewById(R.id.buttonSubmitUpdate);
        imageViewUpdateProfile = findViewById(R.id.imagesViewUpdateProfil);
        uploadImages = findViewById(R.id.UploadImages);
        editTextUpdateUsername = findViewById(R.id.EditTextUpdateUsername);
        editTextUpdateEmail = findViewById(R.id.EditTextUpdateEmail);
        editTextUpdatePassword = findViewById(R.id.EditTextUpdatePassword);
        editTextUpdateNoPhone = findViewById(R.id.EditTextUpdateNoPhone);

        loadProfile();

        buttonSubmitUpdate.setOnClickListener(v -> {
            String username = editTextUpdateUsername.getText().toString();
            String name = editTextUpdateEmail.getText().toString();
            String password = editTextUpdatePassword.getText().toString();
            String phone = editTextUpdateNoPhone.getText().toString();
            String profilePhotoPath = uploadImages.getText().toString();

            UpdateProfile(username,name,password,phone,profilePhotoPath);
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

    private void UpdateProfile(String username, String name, String password, String phone, String profilePhotoPath) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String User_id = sharedPreferences.getString("user_id",null );
        int id = 4; // Gantilah dengan ID pengguna yang benar
        apiInterface.updateProfile(id, username,name,password,phone, profilePhotoPath).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if ((response.isSuccessful())){
                    loadProfile();
                    Intent intent = new Intent(UpdateProfilActivity.this, ProfilFragment.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(UpdateProfilActivity.this, "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(UpdateProfilActivity.this, "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
