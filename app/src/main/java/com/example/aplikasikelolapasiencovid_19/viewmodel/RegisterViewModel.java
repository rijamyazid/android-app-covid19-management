package com.example.aplikasikelolapasiencovid_19.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.repository.Repository;

public class RegisterViewModel extends AndroidViewModel {

    private Repository repository;

    public boolean isUsernameTaken(String username){
        return repository.getAdminByUsername(username) != null;
    }

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insert(Admin admin){
        repository.insertAdmin(admin);
    }

}
