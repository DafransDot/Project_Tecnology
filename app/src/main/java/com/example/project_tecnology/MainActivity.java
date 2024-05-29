package com.example.project_tecnology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project_tecnology.databinding.ActivityMainBinding;
import com.example.project_tecnology.ui.BerandaFragment;
import com.example.project_tecnology.ui.LiveChatFragment;
import com.example.project_tecnology.ui.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    String username, name;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        ini login Register
        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isLoggedIn()){
            moveToLogin();
        }


//        ini fragment
        loadFragment(new BerandaFragment());
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);




    }

   

//    ini login register
    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, Login_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionLogout){
            sessionManager.logoutSession();
            moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }



//    ini Fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.fr_home){
            fragment = new BerandaFragment();
        } else if (item.getItemId() == R.id.fr_news){
            fragment = new NewsFragment();
        }else if (item.getItemId() == R.id.fr_liveChat) {
            fragment = new LiveChatFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null ){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_contener, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}