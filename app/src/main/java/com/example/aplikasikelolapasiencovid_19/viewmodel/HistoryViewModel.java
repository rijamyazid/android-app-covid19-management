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

    public LiveData<List<Pasien>> getAllPasien(){
        return repository.getAllPasien();
    }

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
}
