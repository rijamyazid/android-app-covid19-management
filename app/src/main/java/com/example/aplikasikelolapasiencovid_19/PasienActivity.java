package com.example.aplikasikelolapasiencovid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplikasikelolapasiencovid_19.model.Pasien;
import com.example.aplikasikelolapasiencovid_19.viewmodel.HistoryViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PasienActivity extends AppCompatActivity {

    public static final int RESULT_DELETED = 2;
    public static final String EXTRA_ID =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_NAME";
    public static final String EXTRA_JK =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_JK";
    public static final String EXTRA_USIA =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_USIA";
    public static final String EXTRA_PROVINSI =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_PROVINSI";
    public static final String EXTRA_STATUS =
            "com.example.aplikasikelolapasiencovid_19.EXTRA_STATUS";
    private ArrayAdapter<String> dataAdapter;
    private List<String> jkList = new ArrayList<String>(){
        {
            add("Laki-Laki");
            add("Perempuan");
        }
    };
    private List<String> provinsiList = new ArrayList<String>(){
        {
            add("Jakarta");
            add("Bali");
            add("Bandung");
        }
    };


    private Button btnCancel, btnSave;
    private EditText edtNama, edtUsia;
    private Spinner spinnerProvinsi;
    private RadioGroup rgJK, rgStatus;
    private RadioButton rbJKMale, rbJKFemale, rbStatusSakit, rbStatusSembuh, rbStatusMeninggal, rbSelected;
    private HistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        btnCancel = findViewById(R.id.btn_cancel_add);
        btnSave = findViewById(R.id.btn_save_add);
        edtNama = findViewById(R.id.edt_nama_pasien_add);
        edtUsia = findViewById(R.id.edt_usia_add);
        spinnerProvinsi = findViewById(R.id.spinner_provinsi_add);
        rgJK = findViewById(R.id.rg_jk_add);
        rgStatus = findViewById(R.id.rg_status_add);
        rbJKMale = findViewById(R.id.rb_jk_laki_add);
        rbJKFemale = findViewById(R.id.rb_jk_prm_add);
        rbStatusSakit = findViewById(R.id.rb_status_sakit_add);
        rbStatusSembuh = findViewById(R.id.rb_status_sembuh_add);
        rbStatusMeninggal = findViewById(R.id.rb_status_meninggal_add);
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        setSpinnerValue();

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Data Pasien");
            edtNama.setText(intent.getStringExtra(EXTRA_NAME));
            edtUsia.setText(String.valueOf(intent.getIntExtra(EXTRA_USIA, 0)));

            int rbId = 0;
            if(intent.getStringExtra(EXTRA_JK).equals(rbJKMale.getText().toString())){
                rbId = rbJKMale.getId();
            } else {
                rbId = rbJKFemale.getId();
            }
            rgJK.check(rbId);

            if(intent.getStringExtra(EXTRA_STATUS).equals(rbStatusSakit.getText().toString())){
                rbId = rbStatusSakit.getId();
            } else if(intent.getStringExtra(EXTRA_STATUS).equals(rbStatusSembuh.getText().toString())) {
                rbId = rbStatusSembuh.getId();
            } else {
                rbId = rbStatusMeninggal.getId();
            }
            rgStatus.check(rbId);

            int spinnerPosition = dataAdapter.getPosition(intent.getStringExtra(EXTRA_PROVINSI));
            spinnerProvinsi.setSelection(spinnerPosition);

        } else {
            setTitle("Tambah Data Pasien");
        }

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().hasExtra(EXTRA_ID)){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.pasien_menu, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete_item:

                Intent intent = new Intent();

                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                String name = getIntent().getStringExtra(EXTRA_NAME);
                int usia = getIntent().getIntExtra(EXTRA_USIA, 0);
                String jk = getIntent().getStringExtra(EXTRA_JK);
                String status = getIntent().getStringExtra(EXTRA_STATUS);
                String provinsi = getIntent().getStringExtra(EXTRA_PROVINSI);

                intent.putExtra(EXTRA_ID, id);
                intent.putExtra(EXTRA_NAME, name);
                intent.putExtra(EXTRA_JK, jk);
                intent.putExtra(EXTRA_USIA, usia);
                intent.putExtra(EXTRA_PROVINSI, provinsi);
                intent.putExtra(EXTRA_STATUS, status);

                setResult(RESULT_DELETED, intent);

                finish();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSpinnerValue(){
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinsiList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(dataAdapter);
    }

    private void save(){
        String nama = edtNama.getText().toString();
        String usia = edtUsia.getText().toString();
        String provinsi = spinnerProvinsi.getSelectedItem().toString();

        int rgJKSelect = rgJK.getCheckedRadioButtonId();
        String jk = "";
        if(rgJKSelect != -1){
            rbSelected = findViewById(rgJKSelect);
            jk = rbSelected.getText().toString();
        }
        int rgStatusSelect = rgStatus.getCheckedRadioButtonId();
        String status = "";
        if(rgStatusSelect != -1){
            rbSelected = findViewById(rgStatusSelect);
            status = rbSelected.getText().toString();
        }

        if(!(nama.isEmpty() || usia.isEmpty() || jk.isEmpty() || status.isEmpty() || provinsi.isEmpty())){
            Intent intent = new Intent();
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if(id != -1){
                intent.putExtra(EXTRA_ID, id);
            }
            intent.putExtra(EXTRA_NAME, nama);
            intent.putExtra(EXTRA_JK, jk);
            intent.putExtra(EXTRA_USIA, usia);
            intent.putExtra(EXTRA_STATUS, status);
            intent.putExtra(EXTRA_PROVINSI, provinsi);

            setResult(RESULT_OK, intent);

            finish();

        } else {
            showToast("Field tidak boleh kosong");
        }
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
