package com.example.project_tecnology.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_tecnology.R;
import com.example.project_tecnology.model.liveChat.liveChat;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ChatViewHolder> {

    private final List<liveChat> chatList;

    public chatAdapter(List<liveChat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        liveChat liveChat = chatList.get(position);
        holder.textViewName.setText(liveChat.getName());
        holder.textViewMessage.setText(liveChat.getChat());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewName;
        final TextView textViewMessage;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName =itemView.findViewById(R.id.textViewName);
            textViewMessage =itemView.findViewById(R.id.textViewMessage);
        }
    }
}
