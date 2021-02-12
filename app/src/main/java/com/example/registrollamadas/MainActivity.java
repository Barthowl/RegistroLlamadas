package com.example.registrollamadas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.registrollamadas.adapter.ContactoLlamadasRecyclerAdapter;
import com.example.registrollamadas.adapter.ContactoRecyclerAdapter;
import com.example.registrollamadas.data.DataHolder;
import com.example.registrollamadas.room.ContactoDB;
import com.example.registrollamadas.room.dao.ContactoDao;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;
import com.example.registrollamadas.viewmodel.ViewModelActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISO_CONTACTO = 1;
    private static final int PERMISO_RPS = 2;
    private static final int PERMISO_LOG = 3;


    private ViewModelActivity viewModelActivity;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Contacto> contacto = new ArrayList<>();
    private List<ContactoLlamadas> cl = new ArrayList<>();
    private List<Contacto> lista = DataHolder.getInstance().lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModelActivity = new ViewModelProvider(this).get(ViewModelActivity.class);
        obtenerPermisoContacto();
        obtenerPermisoRPS();
        obtenerPermisoLog();
    }

    private void obtenerPermisoContacto() {
        int result = PackageManager.PERMISSION_GRANTED;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            result = checkSelfPermission(Manifest.permission.READ_CONTACTS);
        }
        if(result == PackageManager.PERMISSION_DENIED) {
            pedirPermisoContactos();
        }
    }

    private void pedirPermisoContactos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                explicarRazon();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISO_CONTACTO);
            }
        }
    }

    private void explicarRazon() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo);
        builder.setMessage(R.string.mensaje);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISO_CONTACTO);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void obtenerPermisoLog() {
        int result = PackageManager.PERMISSION_GRANTED;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            result = checkSelfPermission(Manifest.permission.READ_CALL_LOG);
        }
        if(result == PackageManager.PERMISSION_DENIED) {
            pedirPermisoLogs();
        }
    }

    private void pedirPermisoLogs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)) {
                explicarRazon2();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISO_LOG);
            }
        }
    }

    private void explicarRazon2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo2);
        builder.setMessage(R.string.mensaje2);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISO_LOG);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void obtenerPermisoRPS() {
        int result = PackageManager.PERMISSION_GRANTED;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            result = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        }
        if(result == PackageManager.PERMISSION_DENIED) {
            pedirPermisoRPS();
        }
    }

    private void pedirPermisoRPS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Log.v("XYZ","explico razón RPS");
                explicarRazon3();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISO_RPS);
            }
        }
    }

    private void explicarRazon3() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo3);
        builder.setMessage(R.string.mensaje3);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISO_RPS);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISO_CONTACTO: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    finish();
                } return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.mnHistorial:
                return verHistorial();
            case R.id.mnLlamadas:
                return verLlamadas();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean verLlamadas() {
        cargarContactosLlamadas();
        return true;
    }

    private boolean verHistorial() {
        cargarContactosHistorial();
        return true;
    }

    private void cargarContactosLlamadas() {
        recyclerView = findViewById(R.id.rvcontacto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactoLlamadasRecyclerAdapter(cl);
        recyclerView.setAdapter(adapter);
        viewModelActivity.getListaO().observe(MainActivity.this, new Observer<List<ContactoLlamadas>>() {
            @Override
            public void onChanged(List<ContactoLlamadas> contactos) {
                cl.clear();
                cl.addAll(contactos);
                viewModelActivity.guardarLlamada(contactos);
                adapter.notifyDataSetChanged();
            }
        });
    }



    private void cargarContactosHistorial() {
        recyclerView = findViewById(R.id.rvcontacto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactoRecyclerAdapter(contacto);
        recyclerView.setAdapter(adapter);

        viewModelActivity.getLista().observe(MainActivity.this, new Observer<List<Contacto>>() {
            @Override
            public void onChanged(List<Contacto> contactos) {
                contacto.clear();
                contacto.addAll(contactos);
                viewModelActivity.guardarHistorial(contactos);
                adapter.notifyDataSetChanged();
            }
        });
    }

}