package com.example.registrollamadas;

import android.content.Context;

import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GuardarFicheros {

    Context context;

    public GuardarFicheros(Context context) {
        this.context = context.getApplicationContext();
    }

    //getExternalFilesDir(null); sdcard > android > data > paquete > files > x.csv
    public boolean guardarLlamada(List<ContactoLlamadas> cl) {
        boolean result = true;
        File f = new File(context.getExternalFilesDir(null),"llamadas.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            String cadena = cl.toString();
            String cad = cadena.replaceAll("\\[","");
            String ca = cad.replaceAll("\\]","");
            String[] parte = ca.split(",");
            String nombre = "";
            for(int i = 0; i <= cl.size()-1;i++ ){
                nombre += parte[i] + "\n";
            }
            fw.write(" " + nombre + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    //getFilesDir(); data > data > paquete > files > x.csv
    public boolean guardarHistorial(List<Contacto> c) {
        boolean result = true;
        File f = new File(context.getFilesDir(),"historial.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            String cadena = c.toString();
            String cad = cadena.replaceAll("\\[","");
            String ca = cad.replaceAll("\\]","");
            String[] parte = ca.split(",");
            String nombre = "";
            for(int i = 0; i <= c.size()-1;i++ ){
                nombre += parte[i] + "\n";
            }

            fw.write(" " + nombre + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
