package com.example.aplikasikelolapasiencovid_19.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikelolapasiencovid_19.R;
import com.example.aplikasikelolapasiencovid_19.helper.DBDummy;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ListAdapter<Pasien, HistoryAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener listener = null;

    HistoryAdapter(Context context){
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Pasien> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pasien>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pasien oldItem, @NonNull Pasien newItem) {
            return oldItem.getIdPasien() == newItem.getIdPasien();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pasien oldItem, @NonNull Pasien newItem) {
            return oldItem.getNamaPasien().equals(newItem.getNamaPasien()) &&
                    oldItem.getUsiaPasien() == newItem.getUsiaPasien() &&
                    oldItem.getJenisKelamin().equals(newItem.getJenisKelamin()) &&
                    oldItem.getStatus().equals(newItem.getStatus()) &&
                    oldItem.getProvinsiPasien().equals(newItem.getProvinsiPasien());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pasien pasien = getItem(position);

        holder.tvName.setText(pasien.getNamaPasien());

        holder.tvUsia.setText(String.valueOf(pasien.getUsiaPasien()));

        holder.tvJK.setText(pasien.getJenisKelamin());

        holder.tvProv.setText(pasien.getProvinsiPasien());

        holder.tvId.setText(String.valueOf(pasien.getIdPasien()));

        holder.tvStatus.setText(pasien.getStatus());
        setStatusColor(pasien, holder);
    }

    public Pasien getPasienAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvUsia, tvJK, tvProv, tvId, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_nama);
            tvUsia = itemView.findViewById(R.id.tv_item_usia);
            tvJK = itemView.findViewById(R.id.tv_item_jk);
            tvProv = itemView.findViewById(R.id.tv_item_provinsi);
            tvId = itemView.findViewById(R.id.tv_item_id);
            tvStatus = itemView.findViewById(R.id.tv_item_status);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onClick(getItem(position));
                }
            });

        }
    }

    private void setStatusColor(Pasien pasien, ViewHolder holder){
        if(pasien.getStatus().equals(DBDummy.status.get("Sakit"))){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else if(pasien.getStatus().equals(DBDummy.status.get("Sembuh"))){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorGreenLight));
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRedDark));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(Pasien pasien);
    }
}
