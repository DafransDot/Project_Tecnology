package com.example.project_tecnology.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_tecnology.R;
import com.example.project_tecnology.model.barang.DataBarang;
import com.example.project_tecnology.ui.DetailActivity;

import java.util.List;

public class barangAdapter extends RecyclerView.Adapter<barangAdapter.ViewHolder> {
    private List<DataBarang> dataBarang;
    private Context context;
    int id;

    public barangAdapter(Context context, List<DataBarang> dataBarang) {
        this.dataBarang = dataBarang;
        this.context = context;
    }

    @NonNull
    @Override
    public barangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull barangAdapter.ViewHolder holder, int position) {
        DataBarang barang = dataBarang.get(position);
        holder.tvNama_barang.setText(barang.getNamaBarang());
        holder.tvDescripsi.setText(barang.getHarga());

        String photoBarang = barang.getPhotoBarang();
        if (photoBarang != null && !photoBarang.isEmpty()) {
            try {
                Bitmap bitmap = decodeBase64ToBitmap(photoBarang);
                holder.photo_gambar.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                Log.e("barangAdapter", "Invalid Base64 image string", e);
            }
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("nama_barang", barang.getNamaBarang());
            intent.putExtra("deskripsi", barang.getDeskripsi());
            intent.putExtra("photo_barang", barang.getPhotoBarang());
            intent.putExtra("harga_barang", barang.getHarga());
            intent.putExtra("rating_barang", barang.getRating());
            context.startActivity(intent);
        });
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo_gambar;
        TextView tvNama_barang, tvDescripsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_gambar = itemView.findViewById(R.id.item_barang_ImageView);
            tvNama_barang = itemView.findViewById(R.id.item_barang_NamaBarang);
            tvDescripsi = itemView.findViewById(R.id.item_barang_Deskripsi);

        }
    }
}
