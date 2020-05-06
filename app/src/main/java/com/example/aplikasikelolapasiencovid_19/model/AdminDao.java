package com.example.aplikasikelolapasiencovid_19.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdmin(Admin admin);

    @Update
    void updateAdmin(Admin admin);

    @Delete
    void deleteAdmin(Admin admin);

    @Query("SELECT * FROM admin_table")
    LiveData<List<Admin>> getAllAdmin();

}
