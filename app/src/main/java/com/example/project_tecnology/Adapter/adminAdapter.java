package com.example.project_tecnology.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_tecnology.R;
import com.example.project_tecnology.model.barang.DataResponse;

import java.util.List;

public class adminAdapter extends RecyclerView.Adapter<adminAdapter.AdminViewHolder> {

    private final List<DataResponse> barangList;

    public adminAdapter(List<DataResponse> barangList) {
        this.barangList = barangList;
    }

    @NonNull
    @Override
    public adminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminAdapter.AdminViewHolder holder, int position) {
        DataResponse dataResponse = barangList.get(position);
        //Disini kemungkinan Salah
        String PhotoBarang = String.valueOf(dataResponse.getPhotoBarang());
        String base64Image = PhotoBarang.substring(PhotoBarang.indexOf(",") + 1);
        byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.item_admin_ImageView.setImageBitmap(bitmap);
        holder.item_admin_NamaBarang.setText(dataResponse.getNamaBarang());
        holder.item_admin_Deskripsi.setText(dataResponse.getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        final TextView item_admin_NamaBarang;
        final ImageView item_admin_ImageView;
        final TextView item_admin_Deskripsi;
        Button item_admin_Edit;
        Button item_admin_Delete;
        
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            item_admin_NamaBarang = itemView.findViewById(R.id.item_admin_NamaBarang);
            item_admin_Deskripsi = itemView.findViewById(R.id.item_admin_Deskripsi);
            item_admin_ImageView = itemView.findViewById(R.id.item_admin_ImageView);
            item_admin_Edit = itemView.findViewById(R.id.item_admin_Edit);
            item_admin_Delete = itemView.findViewById(R.id.item_admin_Delete);
            
        }

    }
}
