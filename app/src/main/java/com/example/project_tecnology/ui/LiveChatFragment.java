package com.example.project_tecnology.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.chatAdapter;
import com.example.project_tecnology.R;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.model.liveChat.liveChat;
import com.example.project_tecnology.model.login.LoginData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiveChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private chatAdapter chatadapter;
    private EditText editTextMessage;
    private Button buttonSend;
    private ApiInterface apiInterface;

    private String username;


    public LiveChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiveChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiveChatFragment newInstance(String param1, String param2) {
        LiveChatFragment fragment = new LiveChatFragment();
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonSend.setOnClickListener( v ->{
            String message = editTextMessage.getText().toString();
            if (!TextUtils.isEmpty(message)){
                sendMessage(message);
            }else {
                Toast.makeText(getContext(), "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });
        AmbilChats();


    }

    private void sendMessage(String message) {
        apiInterface.sendChat(username, message).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if ((response.isSuccessful())){
                    editTextMessage.setText("");
                    AmbilChats();
                }else {
                    Toast.makeText(getContext(), "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AmbilChats() {
        apiInterface.getChats().enqueue(new Callback<List<liveChat>>() {
            @Override
            public void onResponse(Call<List<liveChat>> call, Response<List<liveChat>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<liveChat> liveChats = response.body();
                    chatadapter = new chatAdapter(liveChats);
                    recyclerView.setAdapter(chatadapter);
                }else {
                    Toast.makeText(getContext(), "Gagal mengambil pesan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<liveChat>> call, Throwable t) {

            }
        });
    }
}