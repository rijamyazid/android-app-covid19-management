package com.example.aplikasikelolapasiencovid_19.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.repository.Repository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private Repository repository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public boolean isAccountAvailable(String username, String password){
        Admin admin = repository.getAdmin(username, password);
        return admin != null;
    }

    public LiveData<List<Admin>> getAllAdmin(){
        return repository.getAllAdmin();
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
