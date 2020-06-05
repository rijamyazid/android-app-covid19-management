package com.example.aplikasikelolapasiencovid_19.ui;


import android.graphics.Color;
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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.helper.DBDummy;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.viewmodel.HomeViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int[] COLORS_JK = {
            Color.rgb(63, 143, 116),
            Color.rgb(205,67,67)
    };
    private PieDataSet set;
    private PieData data;

    private TextView tvPasien, tvSakitJml, tvSakitPrc, tvSembuhJml, tvSembuhPrc, tvMeninggalJml, tvMeninggalPrc
            , tvPasienSembuh, tvPasienSakit, tvPasienMeninggal, tvMaleCount, tvMaleCountPer, tvFemaleCount, tvFemaleCountPer;
    private HomeViewModel viewModel;
    private PieChart chartPieGenderRasio;

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
        tvMaleCount = view.findViewById(R.id.tv_male_count);
        tvMaleCountPer = view.findViewById(R.id.tv_male_count_per);
        tvFemaleCount = view.findViewById(R.id.tv_female_count);
        tvFemaleCountPer = view.findViewById(R.id.tv_female_count_per);
        chartPieGenderRasio = view.findViewById(R.id.chart_pie_gender_rasio);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        initiateGenderRatioPieChart();

        final List<PieEntry> genderList = new ArrayList<>();
        viewModel.getAllPasien().observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
            @Override
            public void onChanged(List<Pasien> pasiens) {

                int total = pasiens.size();
                int pasienMale = getMaleGenderCount(pasiens);
                int pasienFemale = getFemaleGenderCount(pasiens);

                tvPasien.setText(String.valueOf(pasiens.size()));
                tvPasienSakit.setText(String.valueOf(pasiens.size()));
                tvPasienSembuh.setText(String.valueOf(pasiens.size()));
                tvPasienMeninggal.setText(String.valueOf(pasiens.size()));

                genderList.clear();
                genderList.add(new PieEntry(pasienMale, "Laki-Laki"));
                genderList.add(new PieEntry(pasienFemale, "Perempuan"));
                tvMaleCount.setText(String.valueOf(pasienMale));
                tvMaleCountPer.setText(getString(R.string.percentage_value, percetageValue(pasienMale, total)));
                tvFemaleCount.setText(String.valueOf(pasienFemale));
                tvFemaleCountPer.setText(getString(R.string.percentage_value, percetageValue(pasienFemale, total)));
                setGenderPieChartData(genderList);

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

    private void initiateGenderRatioPieChart(){
        chartPieGenderRasio.setUsePercentValues(true);
        chartPieGenderRasio.getDescription().setEnabled(false);
        chartPieGenderRasio.setExtraOffsets(5,10,5,5);
//        chartPieGenderRasio.setDragDecelerationFrictionCoef(0f);
        chartPieGenderRasio.setDragDecelerationEnabled(false);
        chartPieGenderRasio.setRotationEnabled(false);
        chartPieGenderRasio.setDrawHoleEnabled(false);
    }

    private void setGenderPieChartData(List<PieEntry> genderList){
        set = new PieDataSet(genderList, "Jenis Kelamin");
        set.setSliceSpace(3f);
        set.setSelectionShift(5f);
        set.setColors(COLORS_JK);

        data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);

        chartPieGenderRasio.setData(data);
        chartPieGenderRasio.invalidate();
    }

    private int getMaleGenderCount(List<Pasien> pasiens){
        int count = 0;
        for(Pasien pasien : pasiens){
            if(pasien.getJenisKelamin().equals("Laki-Laki")) ++count;
        }
        return count;
    }

    private int getFemaleGenderCount(List<Pasien> pasiens){
        return pasiens.size() - getMaleGenderCount(pasiens);
    }

    private String percetageValue(double value, double total){
        return new DecimalFormat("##.#").format((value/total)*100);
    }

}

