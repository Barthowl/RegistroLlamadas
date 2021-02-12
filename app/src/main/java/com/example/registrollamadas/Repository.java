package com.example.registrollamadas;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.registrollamadas.room.ContactoDB;
import com.example.registrollamadas.room.dao.ContactoDao;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;
import com.example.registrollamadas.util.UtilThread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Repository {
    private Contacto currentContacto;
    private ContactoDao contactoDao;
    GuardarFicheros guardarFicheros;

    private LiveData<List<Contacto>> liveContactoList;
    private LiveData<List<ContactoLlamadas>> liveContactoLlamadasList;
    private MutableLiveData<Long> liveContactoInsertId = new MutableLiveData<>();

    public Repository(Context context) {
        ContactoDB db = ContactoDB.getDB(context);
        contactoDao = db.getContactoDao();
        liveContactoList = contactoDao.getAll();
        this.guardarFicheros = new GuardarFicheros(context);
    }

    public Contacto getCurrentContacto() {
        return currentContacto;
    }

    public LiveData<List<Contacto>> getLista() {
        liveContactoList = contactoDao.getAll();
        return liveContactoList;
    }

    public LiveData<List<ContactoLlamadas>> getListaO() {
        liveContactoLlamadasList = contactoDao.getAllO();
        return liveContactoLlamadasList;
    }

    public void insert(Contacto c) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = contactoDao.insert(c);
                    liveContactoInsertId.postValue(id);
                } catch (Exception e) {
                    liveContactoInsertId.postValue(0l);
                }
            }
        });
    }

    public void insert(ContactoLlamadas cl) {
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = contactoDao.insert(cl);
                    liveContactoInsertId.postValue(id);
                } catch (Exception e) {
                    liveContactoInsertId.postValue(0l);
                }
            }
        });
    }

    public void delete(long id){
        new Thread(){
            @Override
            public void run() {
                contactoDao.delete(id);
            }
        }.start();
    }

    public boolean guardarHistorial(List<Contacto> c) {
        return guardarFicheros.guardarHistorial(c);
    }

    public boolean guardarLlamada(List<ContactoLlamadas> c) {
        return guardarFicheros.guardarLlamada(c);
    }



}
