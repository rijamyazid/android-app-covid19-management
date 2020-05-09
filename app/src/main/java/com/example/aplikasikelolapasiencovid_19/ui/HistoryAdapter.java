package com.example.aplikasikelolapasiencovid_19.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Pasien> pasienList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pasien pasien = pasienList.get(position);
        holder.tvName.setText(pasien.getNamaPasien());
        holder.tvUsia.setText(String.valueOf(pasien.getUsiaPasien()));
        holder.tvJK.setText(pasien.getJenisKelamin());
        holder.tvProv.setText(pasien.getProvinsiPasien());
    }

    @Override
    public int getItemCount() {
        Log.d("TRACE_ERROR", pasienList.size()+"");
        if(pasienList != null){
            return pasienList.size();
        }
        return 0;
    }

    public void setPasienList(List<Pasien> pasienList) {
        this.pasienList = pasienList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvUsia, tvJK, tvProv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_nama);
            tvUsia = itemView.findViewById(R.id.tv_item_usia);
            tvJK = itemView.findViewById(R.id.tv_item_jk);
            tvProv = itemView.findViewById(R.id.tv_item_provinsi);
        }
    }
}
