package com.example.project_tecnology.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_tecnology.Adapter.chatAdapter;
import com.example.project_tecnology.Login_activity;
import com.example.project_tecnology.MainActivity;
import com.example.project_tecnology.R;
import com.example.project_tecnology.SessionManager;
import com.example.project_tecnology.api.ApiClient;
import com.example.project_tecnology.api.ApiInterface;
import com.example.project_tecnology.databinding.FragmentProfilBinding;
import com.example.project_tecnology.model.liveChat.liveChat;
import com.example.project_tecnology.model.login.Login;
import com.example.project_tecnology.model.login.LoginData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textViewUsername, textViewEmail;
    private ImageView imageViewProfile;
    private Button buttonUpdateProfile, buttonLogout;
    private ApiInterface apiInterface;
    private LoginData loginData;
    private SessionManager sessionManager;


    private  String username, name;



    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        
        sessionManager = new SessionManager(requireActivity());


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        name = sharedPreferences.getString("user", null);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void  onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        buttonUpdateProfile = view.findViewById(R.id.buttonUpdateProfile);
        buttonLogout = view.findViewById(R.id.buttonLogout);


//        Login_activity loginActivity = new Login_activity();
//        int user = loginActivity.id;

        textViewUsername.setText(username);
        textViewEmail.setText(name);


        buttonUpdateProfile.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), UpdateProfilActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Edit Profil", Toast.LENGTH_SHORT).show();
        } );

        buttonLogout.setOnClickListener(v ->{
            sessionManager.logoutSession();
            moveToLogin();


        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(getActivity(), Login_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        requireActivity().finish();
    }


}