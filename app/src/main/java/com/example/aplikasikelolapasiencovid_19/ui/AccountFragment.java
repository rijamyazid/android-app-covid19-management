package com.example.aplikasikelolapasiencovid_19.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aplikasikelolapasiencovid_19.MainActivity;
import com.example.aplikasikelolapasiencovid_19.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private Button btnLogout;
    private SharedPreferences sharedPreferences;

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
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_KEY_LOGIN, Context.MODE_PRIVATE);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.putSharedPrefStatus(sharedPreferences, false);
                MainActivity.putSharedPrefUsername(sharedPreferences, "");
                navigateToLoginFragment(v);
            }
        });
    }

    private void navigateToLoginFragment(View view){
        NavDirections action = AccountFragmentDirections.actionAccountFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }
}
