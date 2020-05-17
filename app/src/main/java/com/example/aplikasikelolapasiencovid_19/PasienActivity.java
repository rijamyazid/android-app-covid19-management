package com.example.aplikasikelolapasiencovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PasienActivity extends AppCompatActivity {

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

    private Button btnCancel, btnSave;
    private EditText edtNama, edtUsia;
    private Spinner spinnerProvinsi;
    private RadioGroup rgJK, rgStatus;
    private RadioButton rbJKMale, rbJKFemale, rbStatusSakit, rbStatusSembuh, rbStatusMeninggal, rbSelected;

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

        setSpinnerValue();

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

    private void setSpinnerValue(){
        List<String> list = new ArrayList<String>(){
            {
                add("Jakarta");
                add("Bali");
                add("Bandung");
            }
        };
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
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
