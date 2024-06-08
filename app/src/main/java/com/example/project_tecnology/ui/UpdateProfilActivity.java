package com.example.project_tecnology.ui;

import static com.example.project_tecnology.R.id.buttonBackToProfil;

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

import com.example.project_tecnology.Adapter.FileUtil;
import com.example.project_tecnology.MainActivity;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.login.LoginData;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button buttonSubmitUpdate;
    private ImageView imageViewUpdateProfile, buttonBackToProfile;
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
            updateProfile(username, name, password, phone);
        });

        uploadImages.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void loadProfile() {

        editTextUpdateUsername.setText("");
        editTextUpdateEmail.setText("");
        editTextUpdatePassword.setText("");
        editTextUpdateNoPhone.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            imageViewUpdateProfile.setImageURI(uri);
        }
    }

    private void updateProfile(String username, String name, String password, String phone) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user", null);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);

        MultipartBody.Part profilePhoto = null;
        if (uri != null) {
            String filePathStr = FileUtil.getPath(this, uri);
            if (filePathStr != null) {
                File file = new File(filePathStr);
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                profilePhoto = MultipartBody.Part.createFormData("profile_photo", file.getName(), requestFile);
            }
        }

        apiInterface.updateProfile(id, usernameBody, nameBody, passwordBody, phoneBody, profilePhoto).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
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
    }
}
