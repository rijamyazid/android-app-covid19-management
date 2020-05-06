package com.example.aplikasikelolapasiencovid_19.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.aplikasikelolapasiencovid_19.db.AppDatabase;
import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.model.AdminDao;

public class Repository {

    private AdminDao adminDao;

    public Repository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        adminDao = db.adminDao();
    }

    public LiveData<Admin> getAdmin(String username){
        return adminDao.getAdmin(username);
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
