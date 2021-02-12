package com.example.registrollamadas.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

import java.util.List;


@Dao
public interface ContactoDao {

    @Insert
    long insert(Contacto c);

    @Insert
    long insert(ContactoLlamadas cl);

    @Query("select * from contactos")
    LiveData<List<Contacto>> getAll();

    @Query("select * from contactos order by nombre asc, fecha")
    LiveData<List<ContactoLlamadas>> getAllO();

    @Update
    int update(Contacto c);

    @Query("DELETE FROM contactos where id = :id")
    int delete(long id);

}
