package com.example.aplikasikelolapasiencovid_19.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.repository.Repository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Pasien>> pasiens;

    public LiveData<List<Pasien>> getAllPasien(){
        return pasiens;
    }

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        pasiens = repository.getAllPasien();
    }
}
