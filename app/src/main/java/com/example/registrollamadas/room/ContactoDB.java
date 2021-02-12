package com.example.registrollamadas.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.registrollamadas.room.dao.ContactoDao;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

@Database(entities = {Contacto.class, ContactoLlamadas.class}, version = 1, exportSchema = false)
public abstract class ContactoDB extends RoomDatabase {

    public abstract ContactoDao getContactoDao();

    private static volatile ContactoDB INSTANCE;


    public static synchronized ContactoDB getDB(final Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ContactoDB.class, "dbcontactos").build();
        }
        return INSTANCE;
    }

}
