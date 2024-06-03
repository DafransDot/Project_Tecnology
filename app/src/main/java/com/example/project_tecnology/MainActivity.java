package com.example.project_tecnology;

//import static com.example.project_tecnology.SessionManager.PHOTO;
import static com.example.project_tecnology.SessionManager.USER_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.project_tecnology.databinding.ActivityMainBinding;
import com.example.project_tecnology.ui.BerandaFragment;
import com.example.project_tecnology.ui.LiveChatFragment;
import com.example.project_tecnology.ui.NewsFragment;
import com.example.project_tecnology.ui.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    public String getUsername() {
        return username;
    }

    String username, name, user_id, Photo;

    SessionManager sessionManager;
    private Fragment currentFra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // Ini login Register
        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);
        user_id = sessionManager.getUserDetail().get(USER_ID); // Convert to int
//        Photo = sessionManager.getUserDetail().get(PHOTO);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("user_id", user_id);
        editor.putString("name", name);
//        editor.putString("photo", Photo);

        editor.apply();

        // Ini fragment
        loadFragment(new BerandaFragment());
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        // Agar fragment tidak sembunyi ketika keyboard dibuka
        final View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                boolean isKeyboardVisible = keypadHeight > screenHeight * 0.15;

                if (isKeyboardVisible) {
                    if (currentFra != null) {
                        getSupportFragmentManager().beginTransaction().hide(currentFra).commit();
                    }
                } else {
                    if (currentFra != null) {
                        getSupportFragmentManager().beginTransaction().show(currentFra).commit();
                    }
                }
            }
        });
    }

    // Ini login register
    public void moveToLogin() {
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
        sessionManager.logoutSession();
        moveToLogin();
        return super.onOptionsItemSelected(item);
    }

    // Ini Fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.fr_home) {
            fragment = new BerandaFragment();
        } else if (item.getItemId() == R.id.fr_shop) {
            fragment = new NewsFragment();
        } else if (item.getItemId() == R.id.fr_liveChat) {
            fragment = new LiveChatFragment();
        } else if (item.getItemId() == R.id.fr_Profil) {
            fragment = new ProfilFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_contener, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
