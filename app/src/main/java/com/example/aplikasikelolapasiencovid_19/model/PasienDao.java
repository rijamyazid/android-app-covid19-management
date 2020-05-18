package com.example.aplikasikelolapasiencovid_19.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasienDao {

    @Insert
    void insert(Pasien pasien);
    @Update
    void update(Pasien pasien);
    @Delete
    void delete(Pasien pasien);

    @Query("SELECT * FROM pasien_table ORDER BY idPasien DESC")
    LiveData<List<Pasien>> getAllPasien();

    @Query("SELECT * FROM pasien_table WHERE status=:status")
    LiveData<List<Pasien>> getPasienByStatus(String status);

}
