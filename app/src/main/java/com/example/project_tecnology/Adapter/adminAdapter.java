package com.example.project_tecnology.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_tecnology.R;
import com.example.project_tecnology.admin.DeleteBarangActivity;
import com.example.project_tecnology.admin.UpdateBarangActivity;
import com.example.project_tecnology.model.barang.DataBarang;

import java.util.ArrayList;
import java.util.List;

public class adminAdapter extends RecyclerView.Adapter<adminAdapter.AdminViewHolder> {

    private List<DataBarang> dataBarang;
    private ArrayList<DataBarang> arrayList;
    private Context context;

    public adminAdapter(Context context, List<DataBarang> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @Override
    public adminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminAdapter.AdminViewHolder holder, int position) {
        DataBarang barang = dataBarang.get(position);
        holder.tvNama_barang.setText(barang.getNamaBarang());
        holder.tvDescripsi.setText(barang.getDeskripsi());
        String harga = barang.getHarga();
        String rating = barang.getRating();


        // Decode Base64 string to Bitmap
        String photoBarang = barang.getPhotoBarang();
        if (photoBarang != null && !photoBarang.isEmpty()) {
            try {
                Bitmap bitmap = decodeBase64ToBitmap(photoBarang);
                holder.photo_gambar.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                Log.e("adminAdapter", "Invalid Base64 image string", e);
            }
        }

        holder.BtnEdit.setOnClickListener(v -> {
            String id = String.valueOf(barang.getId());
            Intent intent = new Intent(context, UpdateBarangActivity.class);

//            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("ID", id);
            intent.putExtra("ID", id);
            Log.d("adminAdapter", "ID: " + id);
            context.startActivity(intent);
        });

        holder.BtnDelete.setOnClickListener(v ->{
            String id = String.valueOf(barang.getId());
            Intent intent = new Intent(context, DeleteBarangActivity.class);

//            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("ID", id);
            intent.putExtra("ID", id);
            Log.d("adminAdapter", "ID: " + id);
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

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        ImageView photo_gambar;
        TextView tvNama_barang, tvDescripsi;
        Button BtnEdit, BtnDelete;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_gambar = itemView.findViewById(R.id.item_admin_ImageView);
            tvNama_barang = itemView.findViewById(R.id.item_admin_NamaBarang);
            tvDescripsi = itemView.findViewById(R.id.item_admin_Deskripsi);
            BtnEdit = itemView.findViewById(R.id.item_admin_Edit);
            BtnDelete = itemView.findViewById(R.id.item_admin_Delete);
        }
    }
}
