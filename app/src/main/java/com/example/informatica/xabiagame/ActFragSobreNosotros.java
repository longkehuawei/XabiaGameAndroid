package com.example.informatica.xabiagame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Informatica on 15/03/2017.
 */

public class ActFragSobreNosotros extends AppCompatActivity{

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    LinearLayout likedin;
    TextView gmail;

    String nameClassMain;
    String nameClassInicio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nosotros);

        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");

        nameClassMain = getIntent().getStringExtra("NAME");
        nameClassInicio = getIntent().getStringExtra("NAME2");


        gmail = (TextView) findViewById(R.id.gmail);
        likedin = (LinearLayout)findViewById(R.id.linkedIn);




        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                Intent.createChooser(emailIntent, "Manda un Email");
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });

        likedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.linkedin.com/in/santi-albus-531985136"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if ((nameClassMain!=null) && (nameClassInicio==null)){
            Intent i = new Intent(ActFragSobreNosotros.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else if ((nameClassInicio!=null) && (nameClassMain==null)){
            Intent i = new Intent(ActFragSobreNosotros.this, InicioJuegoActivity.class);
            i.putExtra("personName", personName);
            i.putExtra("personGivenName", personGivenName);
            i.putExtra("personFamilyName", personFamilyName);
            i.putExtra("personEmail", personEmail);
            i.putExtra("personId", personId);
            startActivity(i);
            finish();
        }
    }

}
