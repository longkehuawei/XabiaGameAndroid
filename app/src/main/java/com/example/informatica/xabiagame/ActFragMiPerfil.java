package com.example.informatica.xabiagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Informatica on 07/10/2016.
 */

public class ActFragMiPerfil extends Activity {

   /** @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mi_perfil, container, false);
    }**/

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    TextView txtNombre, txtApellido, txtUser, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");

        Log.i("HOLA", personName);
        Log.i("HOLA", personGivenName);
        Log.i("HOLA", personFamilyName);
        Log.i("HOLA", personEmail);
        Log.i("HOLA", personId);

        txtNombre = (TextView)findViewById(R.id.txtNombreGet);
        txtApellido = (TextView)findViewById(R.id.txtApeGet);
        txtUser = (TextView)findViewById(R.id.txtGetNomUsu);
        txtEmail = (TextView)findViewById(R.id.txtGetEmail);

        txtNombre.setText(personGivenName);
        txtApellido.setText(personFamilyName);
        txtEmail.setText(personEmail);
        txtUser.setText(personGivenName.concat(personFamilyName));

        ImageButton imgBTN = (ImageButton)findViewById(R.id.imgBtnAtrasMperf);
        imgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActFragMiPerfil.this, InicioJuegoActivity.class);
                i.putExtra("personName", personName);
                i.putExtra("personGivenName", personGivenName);
                i.putExtra("personFamilyName", personFamilyName);
                i.putExtra("personEmail", personEmail);
                i.putExtra("personId", personId);
                startActivity(i);
            }
        });

    }


}
