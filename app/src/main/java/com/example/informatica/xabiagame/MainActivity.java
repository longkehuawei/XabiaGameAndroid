package com.example.informatica.xabiagame;

import android.Manifest;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.informatica.xabiagame.Dialog.OnDialogButton;
//import static com.example.informatica.xabiagame.DialogEntrar.apiClient;


public class MainActivity extends AppCompatActivity{

    private Button btnRout, btnJug;
    private Boolean exit = false;
    private static final String LOG_IP = "LOG";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRout = (Button)findViewById(R.id.btnRutas);
        btnRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent i = new Intent(MainActivity.this, RutasActivity.class);
            startActivity(i);

            }
        });

        btnJug = (Button)findViewById(R.id.btnJug);
        btnJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                new DialogEntrar().show(fragmentManager, "LoginDialog");

            }
        });

        checkLocationStatePermission();

        //Primera Vez metodo
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if(settings.getBoolean("my_first_time", true)){

            Log.d("PRUEBA", "PRIMERA VEZ");

            FragmentManager fragmentManager = getFragmentManager();
            DialogFragment newFragment = new OnDialogButton();
            newFragment.show(fragmentManager, "TAG");

            settings.edit().putBoolean("my_first_time",false).commit();
        }else{
            Log.d("PRUEBA", "SEGUNDA VEZ");
        }


        fab = (FloatingActionButton)findViewById(R.id.fabSobreNos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActFragSobreNosotros.class);
                i.putExtra("NAME", MainActivity.class.getCanonicalName());
                startActivity(i);
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
            System.exit(0);
        } else {
            Toast.makeText(this, "Apreta dos veces para salir.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    private void checkLocationStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No tienes permiso para la localizacion.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para la localizacion");
        }
    }


}

