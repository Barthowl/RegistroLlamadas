package com.example.registrollamadas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.registrollamadas.Repository;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

import java.util.List;

public class ViewModelActivity extends AndroidViewModel {
    private Repository repository;

    public ViewModelActivity(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Contacto getContacto () {
        return repository.getCurrentContacto();
    }

    public LiveData<List<Contacto>> getLista() {
        return repository.getLista();
    }

    public LiveData<List<ContactoLlamadas>> getListaO() {
        return repository.getListaO();
    }

    public void insert(Contacto c) {
        repository.insert(c);
    }

    public void insert(ContactoLlamadas cl) {
        repository.insert(cl);
    }

    public void delete(long id) {
        repository.delete(id);
    }

    public void guardarHistorial(List<Contacto> c){
        repository.guardarHistorial(c);
    }

    public void guardarLlamada(List<ContactoLlamadas> c){
        repository.guardarLlamada(c);
    }


}
