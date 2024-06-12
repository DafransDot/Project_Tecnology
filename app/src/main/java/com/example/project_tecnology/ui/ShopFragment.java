package com.example.project_tecnology.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.project_tecnology.Adapter.searchadapter;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.FragmentShopBinding;
import com.example.project_tecnology.model.barang.BarangResponse;
import com.example.project_tecnology.model.barang.DataBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    private FragmentShopBinding binding;
    private static final String TAG = "ShopFragment";

    private ApiInterface apiInterface;
    private searchadapter adapter;
    private List<DataBarang> dataBarangs;
    private boolean dataLoaded = false;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
        // Initialize the ApiInterface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBarangs = new ArrayList<>();
        adapter = new searchadapter(getContext(), dataBarangs);
        binding.recyclerviewSearhShop.setAdapter(adapter);
        binding.recyclerviewSearhShop.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cari Barang dengan metode pencarian
        binding.etsearchShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Show the FrameLayout when text is entered
                if (s.length() > 0) {
                    binding.FrameSearchShop.setVisibility(View.VISIBLE);
                    String query = binding.etsearchShop.getText().toString().trim();
                    listBarang(query);
                } else {
                    binding.FrameSearchShop.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }
        });

        // Intent ke Shop menggunakan metode Kirim ID
        Intent intent = new Intent(getActivity(), BarangActivity.class);
        binding.btnGotoHandphoneShop.setOnClickListener(v -> {
            intent.putExtra("id", 1);
            startActivity(intent);
        });

        binding.btnGotoLaptop.setOnClickListener(v -> {
            intent.putExtra("id", 2);
            startActivity(intent);
        });

        binding.btnGotoAccessories.setOnClickListener(v -> {
            intent.putExtra("id", 3);
            startActivity(intent);
        });

        binding.btnGotoSmartwatch.setOnClickListener(v -> {
            intent.putExtra("id", 4);
            startActivity(intent);
        });

        binding.btnGotoVideogame.setOnClickListener(v -> {
            intent.putExtra("id", 5);
            startActivity(intent);
        });

        binding.btnGotoSmartTv.setOnClickListener(v -> {
            intent.putExtra("id", 6);
            startActivity(intent);
        });

        binding.btnGotoDrone.setOnClickListener(v -> {
            intent.putExtra("id", 7);
            startActivity(intent);
        });

        binding.btnGotokamera.setOnClickListener(v -> {
            intent.putExtra("id", 8);
            startActivity(intent);
        });

        binding.IMAGEVIEWIphone15.setOnClickListener(v -> {
           findBarangById(98);
        });
        binding.IMAGEVIEWApple.setOnClickListener(v->{
            findBarangById(107);
        });
        binding.IMAGEVIEWAirpods.setOnClickListener(v->{
            findBarangById(18);
        });
        binding.IMAGEVIEWMacbookAir.setOnClickListener(v->{
            findBarangById(102);
        });
        binding.IMAGEVIEWAirpodsPro.setOnClickListener(v->{
            findBarangById(108);
        });
        binding.IMAGEVIEWSamsungS24.setOnClickListener(v->{
            findBarangById(99);
        });
        binding.IMAGEVIEWOppoA60.setOnClickListener(v->{
            findBarangById(100);
        });
        binding.IMAGEVIEWGooglePixel.setOnClickListener(v->{
            findBarangById(101);
        });

        // Panggil listBarang() untuk memuat data
        listBarang(null);
    }

    // List Barang
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
                    dataBarangs.clear();
                    dataBarangs.addAll(response.body().getData());
                    adapter.setDataBarang(dataBarangs);
                    dataLoaded = true; // Set flag bahwa data sudah dimuat
                    Log.d(TAG, "Data berhasil dimuat: " + dataBarangs.toString());
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API call failed", t);
            }
        });
    }

    // Cari barang berdasarkan ID
    private void findBarangById(int id) {
        Call<BarangResponse> call = apiInterface.getBarangById(id);
        call.enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<DataBarang> dataBarangList = response.body().getData();
                    if (!dataBarangList.isEmpty()) {
                        DataBarang barang = dataBarangList.get(0); // Asumsikan hanya satu item yang diambil
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("nama_barang", barang.getNamaBarang());
                        intent.putExtra("deskripsi", barang.getDeskripsi());
                        intent.putExtra("photo_barang", barang.getPhotoBarang());
                        intent.putExtra("harga_barang", barang.getHarga());
                        intent.putExtra("rating_barang", barang.getRating());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Barang tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API call failed", t);
            }
        });
    }
}
