package com.example.aplikasikelolapasiencovid_19.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDummy {

    public static final Map<String, String> status = new HashMap<String, String>(){
        {
            put("Sakit", "Sakit");
            put("Sembuh", "Sembuh");
            put("Meninggal", "Meninggal");
        }
    };

    public static final List<String> LIST_JENIS_KELAMIN = new ArrayList<String>(){
        {
            add("Laki-Laki");
            add("Perempuan");
        }
    };
    public static final List<String> LIST_PROVINSI = new ArrayList<String>(){
        {
            add("Kabupaten Bandung");
            add("Kabupaten Bandung Barat");
            add("Kabupaten Bekasi");
            add("Kabupaten Bogor");
            add("Kabupaten Ciamis");
            add("Kabupaten Cianjur");
            add("Kabupaten Cirebon");
            add("Kabupaten Garut");
            add("Kabupaten Indramayu");
            add("Kabupaten Karawang");
            add("Kabupaten Kuningan");
            add("Kabupaten Majalengka");
            add("Kabupaten Pangandaran");
            add("Kabupaten Purwakarta");
            add("Kabupaten Subang");
            add("Kabupaten Sukabumi");
            add("Kabupaten Sumedang");
            add("Kabupaten Tasikmalaya");
            add("Kota Bandung");
            add("Kota Banjar");
            add("Kota Bekasi");
            add("Kota Bogor");
            add("Kota Cimahi");
            add("Kota Cirebon");
            add("Kota Depok");
            add("Kota Sukabumi");
            add("Kota Tasikmalaya");
        }
    };

}
