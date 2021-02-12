package com.example.registrollamadas.receiver;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.example.registrollamadas.Repository;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.data.DataHolder;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    private Repository repository;
    private List<Contacto> lista = DataHolder.getInstance().lista;
    private Contacto c;
    private ContactoLlamadas cl;
    @Override
    public void onReceive(Context context, Intent intent) {
        repository = new Repository(context);
        final String action = intent.getAction();
        if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if((state.equals(TelephonyManager.EXTRA_STATE_RINGING))){
                        String fecha = new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss", Locale.getDefault()).format(new Date());
                        c = new Contacto();
                        cl = new ContactoLlamadas();
                        cl.setTelefono(incomingNumber);
                        cl.setFecha(fecha);
                        c.setTelefono(incomingNumber);
                        c.setFecha(fecha);
                        String nombre = getContactName(incomingNumber,context);
                        if(nombre.isEmpty()){
                            nombre = "desconocido";
                        }
                        c.setNombre(nombre);
                        cl.setNombre(nombre);
                        repository.insert(c);
                        repository.insert(cl);
            }
        }
    }

    public String getContactName(final String phoneNumber, Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        } else {
            contactName = "desconocido";
        }

        return contactName;
    }
}
