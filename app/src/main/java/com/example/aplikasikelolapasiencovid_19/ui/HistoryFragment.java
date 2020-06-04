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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    private TextView tvLoginReminder;

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
//        tvLoginReminder = view.findViewById(R.id.tv_login_reminder);
        rvMain.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new HistoryAdapter(view.getContext());
        rvMain.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        viewModel.getAllPasien().observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
            @Override
            public void onChanged(List<Pasien> pasiens) {
                if(viewModel.listSize < pasiens.size()){
                    adapter.submitList(pasiens, new Runnable() {
                        @Override
                        public void run() {
                            rvMain.smoothScrollToPosition(0);
                        }
                    });
                    viewModel.listSize = pasiens.size();
                } else {
                    adapter.submitList(pasiens);
                    viewModel.listSize = pasiens.size();
                }
            }
        });

        itemTouchHelper(viewModel, adapter).attachToRecyclerView(rvMain);
//        if(MainActivity.getSharedPrefStatus(sharedPreferences)){
//            tvLoginReminder.setVisibility(View.GONE);
//            fabAddPasien.setVisibility(View.VISIBLE);
//        } else {
//            tvLoginReminder.setVisibility(View.VISIBLE);
//            fabAddPasien.setVisibility(View.GONE);
//        }


        fabAddPasien.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                if(!MainActivity.getSharedPrefStatus(sharedPreferences)){
//                    showToast("Silahkan login terlebih dahulu");
//                    navigateToLoginFragment(v);
//                } else {
                    Intent intent = new Intent(getActivity(), PasienActivity.class);
                    startActivityForResult(intent, MainActivity.REQUEST_ADD_PASIEN);
//                }
            }
        });

        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(Pasien pasien) {
//                if(!MainActivity.getSharedPrefStatus(sharedPreferences)){
//                    showToast("Silahkan login terlebih dahulu");
//                    navigateToLoginFragment(getView());
//                } else {
                    Intent intent = new Intent(getActivity(), PasienActivity.class);
                    intent.putExtra(PasienActivity.EXTRA_ID, pasien.getIdPasien());
                    intent.putExtra(PasienActivity.EXTRA_NAME, pasien.getNamaPasien());
                    intent.putExtra(PasienActivity.EXTRA_USIA, pasien.getUsiaPasien());
                    intent.putExtra(PasienActivity.EXTRA_JK, pasien.getJenisKelamin());
                    intent.putExtra(PasienActivity.EXTRA_PROVINSI, pasien.getProvinsiPasien());
                    intent.putExtra(PasienActivity.EXTRA_STATUS, pasien.getStatus());
                    startActivityForResult(intent, MainActivity.REQUEST_UPDATE_PASIEN);
//                }
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
        } else if(requestCode == MainActivity.REQUEST_UPDATE_PASIEN && resultCode == RESULT_OK){
            int pasienId = data.getIntExtra(PasienActivity.EXTRA_ID, 0);
            String nama = data.getStringExtra(PasienActivity.EXTRA_NAME);
            int usia = Integer.valueOf(data.getStringExtra(PasienActivity.EXTRA_USIA));
            String jk = data.getStringExtra(PasienActivity.EXTRA_JK);
            String provinsi = data.getStringExtra(PasienActivity.EXTRA_PROVINSI);
            String status = data.getStringExtra(PasienActivity.EXTRA_STATUS);
            Pasien pasien = new Pasien(nama, jk, usia, provinsi, status);
            pasien.setIdPasien(pasienId);

            viewModel.updatePasien(pasien);

            showToast("Data tersimpan");
        } else if(requestCode == MainActivity.REQUEST_UPDATE_PASIEN && resultCode == PasienActivity.RESULT_DELETED) {
            int pasienId = data.getIntExtra(PasienActivity.EXTRA_ID, 0);
            String nama = data.getStringExtra(PasienActivity.EXTRA_NAME);
            int usia = data.getIntExtra(PasienActivity.EXTRA_USIA, 0);
            String jk = data.getStringExtra(PasienActivity.EXTRA_JK);
            String provinsi = data.getStringExtra(PasienActivity.EXTRA_PROVINSI);
            String status = data.getStringExtra(PasienActivity.EXTRA_STATUS);
            Pasien pasien = new Pasien(nama, jk, usia, provinsi, status);
            pasien.setIdPasien(pasienId);

            viewModel.deletePasien(pasien);

            showToast("Data dihapus");
        } else {
            showToast("Data tidak disimpan");
        }


    }

    private ItemTouchHelper itemTouchHelper(final HistoryViewModel viewModel, final HistoryAdapter adapter){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                if(!MainActivity.getSharedPrefStatus(sharedPreferences)){
//                    showToast("Silahkan login terlebih dahulu");
//                    navigateToLoginFragment(getView());
//                } else {
                    viewModel.deletePasien(adapter.getPasienAt(viewHolder.getAdapterPosition()));
                    showToast("Data dihapus");
//                }
            }
        });
    }

    private void navigateToLoginFragment(View view){
        NavDirections action = HistoryFragmentDirections.actionHistoryFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
