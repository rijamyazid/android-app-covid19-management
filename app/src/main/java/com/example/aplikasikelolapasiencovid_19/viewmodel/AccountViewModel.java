package com.example.aplikasikelolapasiencovid_19.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.repository.Repository;

public class AccountViewModel extends AndroidViewModel {

    private Repository repository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<Admin> getAdmin(String username){
        return repository.getAdmin(username);
    }

    public void insertAdmin(Admin admin){
        repository.insertAdmin(admin);
    }

    public void updateAdmin(Admin admin){
        repository.updateAdmin(admin);
    }

    public void deleteAdmin(Admin admin){
        repository.deleteAdmin(admin);
    }
}
