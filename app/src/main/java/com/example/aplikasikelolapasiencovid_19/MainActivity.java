package com.example.aplikasikelolapasiencovid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.viewmodel.AccountViewModel;

public class MainActivity extends AppCompatActivity {

    AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getAdmin("Admin").observe(this, new Observer<Admin>() {
            @Override
            public void onChanged(Admin admin) {
                showToast("Admin nya adalah "+admin.getUsername());
            }
        });

    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
