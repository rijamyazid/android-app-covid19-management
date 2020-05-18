package com.example.aplikasikelolapasiencovid_19.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.aplikasikelolapasiencovid_19.db.AppDatabase;
import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.model.AdminDao;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.model.PasienDao;

import java.util.List;

public class Repository {

    private AdminDao adminDao;
    private PasienDao pasienDao;

    public Repository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        adminDao = db.adminDao();
        pasienDao = db.pasienDao();
    }

    public LiveData<List<Pasien>> getAllPasien(){
        return pasienDao.getAllPasien();
    }

    public LiveData<List<Pasien>> getPasienByStatus(String status){
        return pasienDao.getPasienByStatus(status);
    }

    public void insertPasien(Pasien pasien){
        new InsertPasienAsyncTask(pasienDao).execute(pasien);
    }

    public void updatePasien(Pasien pasien){
        new UpdatePasienAsyncTask(pasienDao).execute(pasien);
    }

    public void deletePasien(Pasien pasien){
        new DeletePasienAsyncTask(pasienDao).execute(pasien);
    }

    public Admin getAdmin(String username, String password){
        return adminDao.getAdmin(username, password);
    }

    public Admin getAdminByUsername(String username){
        return adminDao.getAdminByUsername(username);
    }

    public void insertAdmin(Admin admin){
        new InsertAdminAsyncTask(adminDao).execute(admin);
    }

    public void updateAdmin(Admin admin){
        new UpdateAdminAsyncTask(adminDao).execute(admin);
    }

    public void deleteAdmin(Admin admin){
        new DeleteAdminAsyncTask(adminDao).execute(admin);
    }


    private static class InsertPasienAsyncTask extends AsyncTask<Pasien, Void, Void>{

        private PasienDao pasienDao;

        InsertPasienAsyncTask(PasienDao pasienDao){ this.pasienDao = pasienDao; }

        @Override
        protected Void doInBackground(Pasien... pasiens) {
            pasienDao.insert(pasiens[0]);
            return null;
        }
    }

    private static class UpdatePasienAsyncTask extends AsyncTask<Pasien, Void, Void>{

        private PasienDao pasienDao;

        UpdatePasienAsyncTask(PasienDao pasienDao){ this.pasienDao = pasienDao; }

        @Override
        protected Void doInBackground(Pasien... pasiens) {
            pasienDao.update(pasiens[0]);
            return null;
        }
    }

    private static class DeletePasienAsyncTask extends AsyncTask<Pasien, Void, Void>{

        private PasienDao pasienDao;

        DeletePasienAsyncTask(PasienDao pasienDao){ this.pasienDao = pasienDao; }

        @Override
        protected Void doInBackground(Pasien... pasiens) {
            pasienDao.delete(pasiens[0]);
            return null;
        }
    }

    private static class InsertAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;

        InsertAdminAsyncTask(AdminDao adminDao){ this.adminDao = adminDao; }

        @Override
        protected Void doInBackground(Admin... admins) {
            adminDao.insertAdmin(admins[0]);
            return null;
        }
    }

    private static class UpdateAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;

        UpdateAdminAsyncTask(AdminDao adminDao){ this.adminDao = adminDao; }

        @Override
        protected Void doInBackground(Admin... admins) {
            adminDao.updateAdmin(admins[0]);
            return null;
        }
    }

    private static class DeleteAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;

        DeleteAdminAsyncTask(AdminDao adminDao){ this.adminDao = adminDao; }

        @Override
        protected Void doInBackground(Admin... admins) {
            adminDao.deleteAdmin(admins[0]);
            return null;
        }
    }

}
