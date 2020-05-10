package com.example.aplikasikelolapasiencovid_19.helper;

import java.util.HashMap;
import java.util.Map;

public class DBDummy {

    public static Map<String, String> status = new HashMap<String, String>(){
        {
            put("Sakit", "Sakit");
            put("Sembuh", "Sembuh");
            put("Meninggal", "Meninggal");
        }
    };

}
