package com.example.aplikasikelolapasiencovid_19.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aplikasikelolapasiencovid_19.MainActivity;
import com.example.aplikasikelolapasiencovid_19.PasienActivity;
import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.viewmodel.HistoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView rvMain;
    private HistoryViewModel viewModel;
    private HistoryAdapter adapter;
    private FloatingActionButton fabAddPasien;
    private SharedPreferences sharedPreferences;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_KEY_LOGIN, Context.MODE_PRIVATE);
        rvMain = view.findViewById(R.id.rv_main);
        fabAddPasien = view.findViewById(R.id.fab_add_pasien);
        rvMain.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new HistoryAdapter(view.getContext());
        rvMain.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        viewModel.getAllPasien().observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
            @Override
            public void onChanged(List<Pasien> pasiens) {
                adapter.setPasienList(pasiens);
            }
        });

        fabAddPasien.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!MainActivity.getSharedPrefStatus(sharedPreferences)){
                    showToast("Silahkan login terlebih dahulu");
                    navigateToLoginFragment(v);
                } else {
                    Intent intent = new Intent(getActivity(), PasienActivity.class);
                    startActivityForResult(intent, MainActivity.REQUEST_ADD_PASIEN);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQUEST_ADD_PASIEN && resultCode == RESULT_OK){
            String nama = data.getStringExtra(PasienActivity.EXTRA_NAME);
            int usia = Integer.valueOf(data.getStringExtra(PasienActivity.EXTRA_USIA));
            String jk = data.getStringExtra(PasienActivity.EXTRA_JK);
            String provinsi = data.getStringExtra(PasienActivity.EXTRA_PROVINSI);
            String status = data.getStringExtra(PasienActivity.EXTRA_STATUS);
            Pasien pasien = new Pasien(nama, jk, usia, provinsi, status);

            viewModel.insertPasien(pasien);

            showToast("Data tersimpan");
        } else {
            showToast("Data tidak disimpan");
        }
    }

    private void navigateToLoginFragment(View view){
        NavDirections action = HistoryFragmentDirections.actionHistoryFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
