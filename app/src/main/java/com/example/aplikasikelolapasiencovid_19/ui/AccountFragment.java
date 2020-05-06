package com.example.aplikasikelolapasiencovid_19.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.viewmodel.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    AccountViewModel accountViewModel;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        edtUsername = view.findViewById(R.id.edt_username_login);
        edtPassword = view.findViewById(R.id.edt_password_login);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if(accountViewModel.isAccountAvailable(username, password)){
                    showToast("Data tersedia");
                } else {
                    showToast("Data tidak ada");
                }
            }
        });
    }

    private void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
