package com.example.aplikasikelolapasiencovid_19.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.viewmodel.RegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText edtUsername, edtPassword;
    private Button btnDaftar;
    private TextView tvLogin;
    private RegisterViewModel registerViewModel;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = view.findViewById(R.id.edt_username_daftar);
        edtPassword = view.findViewById(R.id.edt_password_daftar);
        btnDaftar = view.findViewById(R.id.btn_daftar);
        tvLogin = view.findViewById(R.id.tv_login);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        btnDaftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(!(username.trim().isEmpty() || password.trim().isEmpty())) {
                    if(!registerViewModel.isUsernameTaken(username)){
                        registerViewModel.insert(new Admin(username, password));
                        showToast("Data sudah disimpan, silahkan login");
                        navigateToLoginFragment(v);
                    } else {
                        showToast("Username sudah digunakan orang lain");
                    }
                } else {
                    showToast("Field harus diisi, tidak boleh kosong!");
                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navigateToLoginFragment(v);
            }
        });

    }

    private void navigateToLoginFragment(View view){
        NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
