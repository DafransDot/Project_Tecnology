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

import java.util.ArrayList;
import java.util.List;

public class searchadapter extends RecyclerView.Adapter<searchadapter.viewHolder>{
    private List<DataBarang> dataBarang;
    private ArrayList<DataBarang> arrayList;
    private Context context;
    public searchadapter(Context context, List<DataBarang> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @Override
    public searchadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchadapter.viewHolder holder, int position) {
        DataBarang barang = dataBarang.get(position);
        holder.namaBarang.setText(barang.getNamaBarang());

        String photoBarang = barang.getPhotoBarang();
        if (photoBarang != null && !photoBarang.isEmpty()) {
            try {
                Bitmap bitmap = decodeBase64ToBitmap(photoBarang);
                holder.photo_barang.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                Log.e("adminAdapter", "Invalid Base64 image string", e);
            }
        }

        holder.itemView.setOnClickListener(v->{
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
    public void setDataBarang(List<DataBarang> dataBarang) {
        this.dataBarang = dataBarang;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView photo_barang;
        TextView namaBarang;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            photo_barang = itemView.findViewById(R.id.item_search_photo);
            namaBarang = itemView.findViewById(R.id.item_search_tvnamabarang);
        }
    }
}
