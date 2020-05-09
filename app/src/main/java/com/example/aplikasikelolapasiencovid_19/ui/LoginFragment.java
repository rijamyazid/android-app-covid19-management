package com.example.aplikasikelolapasiencovid_19.ui;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.aplikasikelolapasiencovid_19.MainActivity;
import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.viewmodel.LoginViewModel;

import static com.example.aplikasikelolapasiencovid_19.MainActivity.putSharedPrefStatus;
import static com.example.aplikasikelolapasiencovid_19.MainActivity.putSharedPrefUsername;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText edtUsername, edtPassword;
    TextView tvDaftar;
    Button btnLogin;
    LoginViewModel loginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_KEY_LOGIN, Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean(MainActivity.SHARED_PREF_KEY_LOGIN_STATUS, false))
            navigateToAccountFragment(view);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        edtUsername = view.findViewById(R.id.edt_username_login);
        edtPassword = view.findViewById(R.id.edt_password_login);
        tvDaftar = view.findViewById(R.id.tv_daftar);
        btnLogin = view.findViewById(R.id.btn_login);
        tvDaftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navigateToRegisterFragment(v);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if(!(username.trim().isEmpty() || password.trim().isEmpty())){
                    if(loginViewModel.isAccountAvailable(username, password)){
                        putSharedPrefStatus(sharedPreferences, true);
                        putSharedPrefUsername(sharedPreferences, username);
                        navigateToAccountFragment(v);
                    } else {
                        showToast("Username atau password yang dimasukan salah");
                    }
                } else {
                    showToast("Field tidak boleh kosong!");
                }
            }
        });
    }

    private void navigateToAccountFragment(View view){
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToAccountFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void navigateToRegisterFragment(View view){
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
