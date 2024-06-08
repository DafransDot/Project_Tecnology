package com.example.project_tecnology.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_tecnology.R;
import com.example.project_tecnology.databinding.FragmentBerandaBinding;

public class BerandaFragment extends Fragment {
    private FragmentBerandaBinding binding;

    private String username, name, Photo, userId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BerandaFragment() {
        // Required empty public constructor
    }

    public static BerandaFragment newInstance(String param1, String param2) {
        BerandaFragment fragment = new BerandaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using view binding
        binding = FragmentBerandaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Go to Profil dengan ImagesView
        binding.TEXTVIEWProfil.setOnClickListener(v -> {
            // Menggunakan fragment manager untuk menampilkan ProfilFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_contener, new ProfilFragment())
                    .addToBackStack(null)  // Ini penting untuk memungkinkan navigasi kembali
                    .commit();
        });

        // intent ke barang metode menggunakan ID
        Intent intentGotoBarang = new Intent(getContext(), BarangActivity.class);

        binding.btnGotoHandphone.setOnClickListener(v -> {
            intentGotoBarang.putExtra("id",1);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoLaptop.setOnClickListener(v ->{
            intentGotoBarang.putExtra("id", 2);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoAccessories.setOnClickListener(v ->{
            intentGotoBarang.putExtra("id", 3);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoSmartwatch.setOnClickListener( v -> {
            intentGotoBarang.putExtra("id", 4);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoVideogame.setOnClickListener( v -> {
            intentGotoBarang.putExtra("id", 5);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoSmartTv.setOnClickListener( v -> {
            intentGotoBarang.putExtra("id", 6);
            startActivity(intentGotoBarang);
        });

        binding.btnGotoDrone.setOnClickListener( v -> {
            intentGotoBarang.putExtra("id", 7);
            startActivity(intentGotoBarang);
        });

        binding.btnGotokamera.setOnClickListener( v -> {
            intentGotoBarang.putExtra("id", 8);
            startActivity(intentGotoBarang);
        });

        //Ngambil Data Dari MainActivity
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        name = sharedPreferences.getString("name", null);
        userId = sharedPreferences.getString("user", null);
        Log.d("aokwakawokawo", "awoawkoawkaowkaooawkaow"+ username);
        Log.d("aokwakawokawo", "awoawkoawkaowkaooawkaow"+ name);

        //TextView ISI USERNAME DAN JUGA EMAIL
        binding.TextViewUsername.setText(username);
        binding.TextViewNomor.setText(name);




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Menghindari kebocoran memori
    }
}
