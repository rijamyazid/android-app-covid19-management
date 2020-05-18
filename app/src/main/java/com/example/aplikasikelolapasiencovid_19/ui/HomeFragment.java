package com.example.aplikasikelolapasiencovid_19.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.helper.DBDummy;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.viewmodel.HomeViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView tvPasien, tvSakitJml, tvSakitPrc, tvSembuhJml, tvSembuhPrc, tvMeninggalJml, tvMeninggalPrc
            , tvPasienSembuh, tvPasienSakit, tvPasienMeninggal;
    private HomeViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPasien = view.findViewById(R.id.tv_pasien_jml_home);
        tvPasienSakit = view.findViewById(R.id.tv_sakit_pasien_jml_home);
        tvPasienSembuh = view.findViewById(R.id.tv_sembuh_pasien_jml_home);
        tvPasienMeninggal = view.findViewById(R.id.tv_meninggal_pasien_jml_home);
        tvSakitJml = view.findViewById(R.id.tv_sakit_jml_home);
        tvSembuhJml = view.findViewById(R.id.tv_sembuh_jml_home);
        tvMeninggalJml = view.findViewById(R.id.tv_meninggal_jml_home);
        tvSakitPrc = view.findViewById(R.id.tv_sakit_percent_home);
        tvSembuhPrc = view.findViewById(R.id.tv_sembuh_percent_home);
        tvMeninggalPrc = view.findViewById(R.id.tv_meninggal_percent_home);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        viewModel.getAllPasien().observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
            @Override
            public void onChanged(List<Pasien> pasiens) {
                tvPasien.setText(String.valueOf(pasiens.size()));
                tvPasienSakit.setText(String.valueOf(pasiens.size()));
                tvPasienSembuh.setText(String.valueOf(pasiens.size()));
                tvPasienMeninggal.setText(String.valueOf(pasiens.size()));
                viewModel.getPasienByStatus(DBDummy.status.get("Sakit")).observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
                    @Override
                    public void onChanged(List<Pasien> pasiens) {
                        tvSakitJml.setText(String.valueOf(pasiens.size()));
                        double textPrc = (pasiens.size()/Double.valueOf(tvPasien.getText().toString()))*100;
                        tvSakitPrc.setText(String.valueOf(textPrc));
                    }
                });
                viewModel.getPasienByStatus(DBDummy.status.get("Sembuh")).observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
                    @Override
                    public void onChanged(List<Pasien> pasiens) {
                        tvSembuhJml.setText(String.valueOf(pasiens.size()));
                        double textPrc = (pasiens.size()/Double.valueOf(tvPasien.getText().toString()))*100;
                        tvSembuhPrc.setText(String.valueOf(textPrc));
                    }
                });
                viewModel.getPasienByStatus(DBDummy.status.get("Meninggal")).observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
                    @Override
                    public void onChanged(List<Pasien> pasiens) {
                        tvMeninggalJml.setText(String.valueOf(pasiens.size()));
                        double textPrc = (pasiens.size()/Double.valueOf(tvPasien.getText().toString()))*100;
                        tvMeninggalPrc.setText(String.valueOf(textPrc));
                    }
                });
            }
        });
    }
}
