package com.example.aplikasikelolapasiencovid_19.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "admin_table")
public class Admin {

    @PrimaryKey
    @NonNull
    private String username;
    private String password;

    public Admin(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
