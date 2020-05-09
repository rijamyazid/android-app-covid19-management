package com.example.aplikasikelolapasiencovid_19.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pasien_table")
public class Pasien {

    @PrimaryKey(autoGenerate = true)
    private int idPasien;
    private String namaPasien;
    private String jenisKelamin;
    private int usiaPasien;
    private String provinsiPasien;

    public Pasien(String namaPasien, String jenisKelamin, int usiaPasien, String provinsiPasien) {
        this.namaPasien = namaPasien;
        this.jenisKelamin = jenisKelamin;
        this.usiaPasien = usiaPasien;
        this.provinsiPasien = provinsiPasien;
    }

    public void setIdPasien(int idPasien) {
        this.idPasien = idPasien;
    }

    public int getIdPasien() {
        return idPasien;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public int getUsiaPasien() {
        return usiaPasien;
    }

    public String getProvinsiPasien() {
        return provinsiPasien;
    }
}
