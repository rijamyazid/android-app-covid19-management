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
    private static final int[] COLORS_STATUS = {
            Color.rgb(44,52,124),
            Color.rgb(60,147,139),
            Color.rgb(234,108,85)
    };

    private PieDataSet setPie;
    private PieData dataPie;
    private PieDataSet setStatus;
    private PieData dataStatus;

    private TextView tvPasien, tvMaleCount, tvMaleCountPer, tvFemaleCount, tvFemaleCountPer,
            tvSakitCount, tvSakitCountPer, tvSembuhCount, tvSembuhCountPer, tvMeninggalCount, tvMeninggalCountPer;
    private HomeViewModel viewModel;
    private PieChart chartPieGenderRasio, chartPieStatusRasio;

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
        tvMaleCount = view.findViewById(R.id.tv_male_count);
        tvMaleCountPer = view.findViewById(R.id.tv_male_count_per);
        tvFemaleCount = view.findViewById(R.id.tv_female_count);
        tvFemaleCountPer = view.findViewById(R.id.tv_female_count_per);
        tvSakitCount = view.findViewById(R.id.tv_sakit_count);
        tvSakitCountPer = view.findViewById(R.id.tv_sakit_count_per);
        tvSembuhCount = view.findViewById(R.id.tv_sembuh_count);
        tvSembuhCountPer = view.findViewById(R.id.tv_sembuh_count_per);
        tvMeninggalCount = view.findViewById(R.id.tv_meninggal_count);
        tvMeninggalCountPer = view.findViewById(R.id.tv_meninggal_count_per);
        chartPieGenderRasio = view.findViewById(R.id.chart_pie_gender_rasio);
        chartPieStatusRasio = view.findViewById(R.id.chart_pie_status_rasio);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        initiateGenderRatioPieChart();
        initiateStatusRatioPieChart();

        final List<PieEntry> genderList = new ArrayList<>();
        final List<PieEntry> statusList = new ArrayList<>();
        viewModel.getAllPasien().observe(getViewLifecycleOwner(), new Observer<List<Pasien>>() {
            @Override
            public void onChanged(List<Pasien> pasiens) {

                int total = pasiens.size();
                int pasienMale = getMaleGenderCount(pasiens);
                int pasienFemale = getFemaleGenderCount(pasiens);
                int statusSakit = getStatusSakitCount(pasiens);
                int statusSembuh = getStatusSembuhCount(pasiens);
                int statusMeninggal = getStatusMeninggalCount(pasiens);

                tvPasien.setText(String.valueOf(pasiens.size()));

                genderList.clear();
                genderList.add(new PieEntry(pasienMale, "Laki-Laki"));
                genderList.add(new PieEntry(pasienFemale, "Perempuan"));
                tvMaleCount.setText(String.valueOf(pasienMale));
                tvMaleCountPer.setText(getString(R.string.percentage_value, percetageValue(pasienMale, total)));
                tvFemaleCount.setText(String.valueOf(pasienFemale));
                tvFemaleCountPer.setText(getString(R.string.percentage_value, percetageValue(pasienFemale, total)));
                setGenderPieChartData(genderList);

                statusList.clear();
                statusList.add(new PieEntry(statusSakit, "Sakit"));
                statusList.add(new PieEntry(statusSembuh, "Sembuh"));
                statusList.add(new PieEntry(statusMeninggal, "Meninggal"));
                tvSakitCount.setText(String.valueOf(statusSakit));
                tvSakitCountPer.setText(getString(R.string.percentage_value, percetageValue(statusSakit, total)));
                tvSembuhCount.setText(String.valueOf(statusSembuh));
                tvSembuhCountPer.setText(getString(R.string.percentage_value, percetageValue(statusSembuh, total)));
                tvMeninggalCount.setText(String.valueOf(statusMeninggal));
                tvMeninggalCountPer.setText(getString(R.string.percentage_value, percetageValue(statusMeninggal, total)));
                setStatusPieChartData(statusList);

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

    private void initiateStatusRatioPieChart(){
        chartPieStatusRasio.setUsePercentValues(true);
        chartPieStatusRasio.getDescription().setEnabled(false);
        chartPieStatusRasio.setExtraOffsets(5,10,5,5);
//        chartPieGenderRasio.setDragDecelerationFrictionCoef(0f);
        chartPieStatusRasio.setDragDecelerationEnabled(false);
        chartPieStatusRasio.setRotationEnabled(false);
        chartPieStatusRasio.setDrawHoleEnabled(false);
    }

    private void setGenderPieChartData(List<PieEntry> genderList){
        setPie = new PieDataSet(genderList, "Jenis Kelamin");
        setPie.setSliceSpace(3f);
        setPie.setSelectionShift(5f);
        setPie.setColors(COLORS_JK);

        dataPie = new PieData(setPie);
        dataPie.setValueFormatter(new PercentFormatter());
        dataPie.setValueTextSize(15f);
        dataPie.setValueTextColor(Color.WHITE);

        chartPieGenderRasio.setData(dataPie);
        chartPieGenderRasio.invalidate();
    }

    private void setStatusPieChartData(List<PieEntry> genderList){
        setStatus = new PieDataSet(genderList, "Status");
        setStatus.setSliceSpace(3f);
        setStatus.setSelectionShift(5f);
        setStatus.setColors(COLORS_STATUS);

        dataStatus = new PieData(setStatus);
        dataStatus.setValueFormatter(new PercentFormatter());
        dataStatus.setValueTextSize(15f);
        dataStatus.setValueTextColor(Color.WHITE);

        chartPieStatusRasio.setData(dataStatus);
        chartPieStatusRasio.invalidate();
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

    private int getStatusSakitCount(List<Pasien> pasiens){
        int count = 0;
        for(Pasien pasien : pasiens){
            if(pasien.getStatus().equals("Sakit")) ++count;
        }
        return count;
    }

    private int getStatusSembuhCount(List<Pasien> pasiens){
        int count = 0;
        for(Pasien pasien : pasiens){
            if(pasien.getStatus().equals("Sembuh")) ++count;
        }
        return count;
    }

    private int getStatusMeninggalCount(List<Pasien> pasiens){
        int count = 0;
        for(Pasien pasien : pasiens){
            if(pasien.getStatus().equals("Meninggal")) ++count;
        }
        return count;
    }

    private String percetageValue(double value, double total){
        return new DecimalFormat("##.#").format((value/total)*100);
    }

}

