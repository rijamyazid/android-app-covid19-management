package com.example.aplikasikelolapasiencovid_19.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aplikasikelolapasiencovid_19.model.Admin;
import com.example.aplikasikelolapasiencovid_19.model.AdminDao;
import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.model.PasienDao;

@Database(entities = {Admin.class, Pasien.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE = null;

    public abstract AdminDao adminDao();

    public abstract PasienDao pasienDao();

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null) INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build();
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyntask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyntask extends AsyncTask<Void, Void, Void> {

        private AdminDao adminDao;
        private PasienDao pasienDao;

        private PopulateDbAsyntask(AppDatabase db){
            this.adminDao = db.adminDao();
            this.pasienDao = db.pasienDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            adminDao.insertAdmin(new Admin("Admin", "Admin"));
            pasienDao.insert(new Pasien("Feggy Rizkiana", "Laki-Laki", 36, "Kabupaten Bandung", "Sakit"));
            pasienDao.insert(new Pasien("Maulana Alif", "Laki-Laki", 30, "Kota Depok", "Sembuh"));
            pasienDao.insert(new Pasien("Hilda Esfandiari", "Perempuan", 25, "Kabupaten Bogor", "Meninggal"));
            return null;
        }
    }

}
