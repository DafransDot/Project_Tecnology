package com.example.project_tecnology.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_tecnology.Login_activity;
import com.example.project_tecnology.R;
import com.example.project_tecnology.SessionManager;
import com.example.project_tecnology.admin.AdminActivity;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.ApiResponse;
import com.example.project_tecnology.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilFragment extends Fragment {

    private TextView textViewUsername, textViewEmail, textViewPhone, textViewFullname;
    private ImageView imageViewProfile;
    private Button buttonUpdateProfile, buttonLogout, buttonAbout, btnAdmin;
    private ApiInterface apiInterface;
    private SessionManager sessionManager;
    int id;

    private String username, name, Photo, userId, fullname, phone;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString("param1");
            name = getArguments().getString("param2");
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(requireActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        name = sharedPreferences.getString("name", null);
        Log.d("profil", "Terikrim : " + name);
        userId = sharedPreferences.getString("user", null);
        Log.d("Profil ", "ID terkirim  " + userId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewFullname = view.findViewById(R.id.textViewFullName);
        textViewPhone = view.findViewById(R.id.textViewNo);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        buttonUpdateProfile = view.findViewById(R.id.buttonUpdateProfile);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonAbout = view.findViewById(R.id.buttonAbout);
        btnAdmin = view.findViewById(R.id.btnAdmin);

        buttonUpdateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateProfilActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Edit Profil", Toast.LENGTH_SHORT).show();
        });

        buttonAbout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Edit Profil", Toast.LENGTH_SHORT).show();
        });

        buttonLogout.setOnClickListener(v -> {
            sessionManager.logoutSession();
            moveToLogin();
        });



        if (userId != null && !userId.isEmpty()) {
            try {
                id = Integer.parseInt(userId);
                Log.d("Profile", "User ID: " + id);
                getUserProfile(id);
            } catch (NumberFormatException e) {
                Log.e("Profile", "NumberFormatException: " + e.getMessage());
                Toast.makeText(getContext(), "Invalid user ID format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("Profile", "User ID is null or empty");
        }

        if (id == 1){
            btnAdmin.setVisibility(View.VISIBLE);
            btnAdmin.setOnClickListener( v->{
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            });
        }

    }

    private void moveToLogin() {
        Intent intent = new Intent(getActivity(), Login_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        requireActivity().finish();
    }

    public Bitmap decodeBase64ToBitmap(String base64str) throws IllegalArgumentException {
        byte[] decodedByte = Base64.decode(base64str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void getUserProfile(int userId) {
        apiInterface.getUserProfile(userId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    LoginData loginData = apiResponse.getData();

                    if (loginData != null) {
                        username = loginData.getUsername();
                        name = loginData.getName();
                        Photo = loginData.getProfilePhotoPath();
                        phone = loginData.getPhone();
                        fullname = loginData.getFullname();


                        textViewUsername.setText(username);
                        textViewEmail.setText(name);
                        textViewFullname.setText(fullname);
                        textViewPhone.setText(phone);

                        if (Photo != null && !Photo.isEmpty()) {
                            try {
                                String base64Image = Photo.substring(Photo.indexOf(",") + 1);
                                byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                imageViewProfile.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                Log.e("Profil", "Error decoding image", e);
                            }
                        } else {
                            Log.d("Profil", "Photo is null or empty");
                        }
                    } else {
                        Log.d("Profil", "No data in LoginData");
                    }
                } else {
                    Log.d("Profil", "Failed to get user profile");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Profil", "API call failed", t);
            }
        });
    }
}
