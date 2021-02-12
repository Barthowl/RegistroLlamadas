package com.example.registrollamadas.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactos2")
public class ContactoLlamadas {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;

    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;


    public ContactoLlamadas(){}

    public ContactoLlamadas(String nombre, String telefono, String fecha) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return nombre + "; " + fecha + "; " + telefono;
    }


}