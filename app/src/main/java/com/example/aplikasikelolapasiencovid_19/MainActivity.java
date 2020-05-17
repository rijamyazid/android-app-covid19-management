package com.example.aplikasikelolapasiencovid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREF_KEY_LOGIN =
            "com.example.aplikasikelolapasiencovid_19.SHARED_PREF_KEY_LOGIN";
    public static final String SHARED_PREF_KEY_LOGIN_STATUS =
            "com.example.aplikasikelolapasiencovid_19.SHARED_PREF_KEY_LOGIN_STATUS";
    public static final String SHARED_PREF_KEY_LOGIN_USERNAME =
            "com.example.aplikasikelolapasiencovid_19.SHARED_PREF_KEY_LOGIN_USERNAME";

    public static final int REQUEST_ADD_PASIEN = 1;
    public static final int REQUEST_UPDATE_PASIEN = 2;

    private BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvMain = findViewById(R.id.bnv_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavigationUI.setupWithNavController(bnvMain, navHostFragment.getNavController());

    }

    public static void putSharedPrefStatus(SharedPreferences sharedPreferences, boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.SHARED_PREF_KEY_LOGIN_STATUS, status);
        editor.apply();
    }

    public static void putSharedPrefUsername(SharedPreferences sharedPreferences, String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.SHARED_PREF_KEY_LOGIN_USERNAME, username);
        editor.apply();
    }

    public static boolean getSharedPrefStatus(SharedPreferences sharedPreferences){
        return sharedPreferences.getBoolean(MainActivity.SHARED_PREF_KEY_LOGIN_STATUS, false);
    }
}
