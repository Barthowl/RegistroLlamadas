package com.example.registrollamadas.data;

import com.example.registrollamadas.room.pojo.Contacto;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
     public final List<Contacto> lista = new ArrayList<Contacto>();

    private DataHolder() {}

    public static com.example.registrollamadas.data.DataHolder getInstance() {
        if( instance == null ) {
            instance = new com.example.registrollamadas.data.DataHolder();
        }
        return instance;
    }

    private static com.example.registrollamadas.data.DataHolder instance;
}
