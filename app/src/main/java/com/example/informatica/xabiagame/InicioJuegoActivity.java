package com.example.informatica.xabiagame;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.Adapters.CardViewMisiones;
import com.example.informatica.xabiagame.Dialog.BackButtonListener;
import com.example.informatica.xabiagame.model.Mision;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.informatica.xabiagame.R.id.navview;

/**
 * Created by Informatica on 06/10/2016.
 */

public class InicioJuegoActivity extends AppCompatActivity {


    NavigationView nv;
    DrawerLayout dl;
    private static final String LOLA = "";
    static GoogleApiClient apiClient;

    /*ListView lv;
    ArrayList<Mision> art;
    MisionesAdapterDefinitiva<Mision> adapterMision;*/

    RecyclerView rv;
    RecyclerView.Adapter rca;
    List<Mision> list;
    private ProgressDialog progressDialog1;

    Gson gson = new Gson();
    private static final String TAG = "Prueba";

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    FloatingActionButton fab, fabAyuda, fabAtras;
    Animation fabOpen, fabClosed, fabclockview, fabranticlockwise;
    private boolean isOpen = false;
    private boolean exit = false;

    TextView txtAyuda, txtAtras;

    public InicioJuegoActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inicio_juego_activity);

        nv = (NavigationView)findViewById(navview);
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);

        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");

        /*LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view2);
        animationView.setAnimation("pinjump.json");
        animationView.loop(true);
        animationView.playAnimation();*/

        TextView navViewUser = (TextView) nv.getHeaderView(0).findViewById(R.id.txtNavView);
        navViewUser.setText(personName);

        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_perfil:
                                Log.i(LOLA, "menu perfil.");
                                Intent i = new Intent(InicioJuegoActivity.this, ActFragMiPerfil.class);
                                i.putExtra("personName", personName);
                                i.putExtra("personGivenName", personGivenName);
                                i.putExtra("personFamilyName", personFamilyName);
                                i.putExtra("personEmail", personEmail);
                                i.putExtra("personId", personId);

                                Log.i(TAG, personName);
                                Log.i(TAG, personGivenName);
                                Log.i(TAG, personFamilyName);
                                Log.i(TAG, personEmail);
                                Log.i(TAG, personId);

                                startActivity(i);
                                break;
                            case R.id.menu_gymkh_compl:
                                Intent u = new Intent(InicioJuegoActivity.this, ActFragGymksCompletados.class);
                                u.putExtra("personName", personName);
                                u.putExtra("personGivenName", personGivenName);
                                u.putExtra("personFamilyName", personFamilyName);
                                u.putExtra("personEmail", personEmail);
                                u.putExtra("personId", personId);

                                Log.i(TAG, personName);
                                Log.i(TAG, personGivenName);
                                Log.i(TAG, personFamilyName);
                                Log.i(TAG, personEmail);
                                Log.i(TAG, personId);

                                startActivity(u);
                                break;
                            case R.id.abouThisApp:
                                Intent y = new Intent(InicioJuegoActivity.this, ActFragSobreNosotros.class);
                                y.putExtra("personName", personName);
                                y.putExtra("personGivenName", personGivenName);
                                y.putExtra("personFamilyName", personFamilyName);
                                y.putExtra("personEmail", personEmail);
                                y.putExtra("personId", personId);

                                y.putExtra("NAME2", InicioJuegoActivity.class.getCanonicalName());

                                Log.i(TAG, personName);
                                Log.i(TAG, personGivenName);
                                Log.i(TAG, personFamilyName);
                                Log.i(TAG, personEmail);
                                Log.i(TAG, personId);

                                startActivity(y);
                                break;
                            case R.id.menu_cerrarsesion:
                                Intent o = new Intent(InicioJuegoActivity.this, MainActivity.class);
                                startActivity(o);
                                showProgressDialog();
                                break;
                        }
                        dl.closeDrawer(GravityCompat.START);
                        return true;

                    }
        });

        rv = (RecyclerView)findViewById(R.id.recyclerViewMisiones);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        cargarAdaptador();

        hideProgressDialog();

        //firsTimeHelpMode();

        //fabs
        fabAyuda = (FloatingActionButton)findViewById(R.id.fabButHelp);
        fab = (FloatingActionButton)findViewById(R.id.fabButPlus);
        fabAtras = (FloatingActionButton)findViewById(R.id.fabBut);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClosed = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fabclose);
        fabranticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        fabclockview = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);

        txtAtras = (TextView)findViewById(R.id.txtAtras);
        txtAyuda = (TextView)findViewById(R.id.txtAyuda);

        fabAtras.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onBackPressed();
                                            }
                                        });

                    fabAyuda.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent y = new Intent(InicioJuegoActivity.this, DialogHelperForNoobs.class);
                            y.putExtra("personName", personName);
                            y.putExtra("personGivenName", personGivenName);
                            y.putExtra("personFamilyName", personFamilyName);
                            y.putExtra("personEmail", personEmail);
                            y.putExtra("personId", personId);

                            startActivity(y);
                        }
                    });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){
                    fabAyuda.startAnimation(fabClosed);
                    fabAtras.startAnimation(fabClosed);
                    fab.startAnimation(fabranticlockwise);
                    fabAyuda.setClickable(false);
                    fabAtras.setClickable(false);
                    txtAtras.startAnimation(fabClosed);
                    txtAyuda.startAnimation(fabClosed);
                    txtAtras.setClickable(false);
                    txtAyuda.setClickable(false);
                    isOpen = false;
                }else{
                    fabAyuda.startAnimation(fabOpen);
                    fabAtras.startAnimation(fabOpen);
                    fab.startAnimation(fabclockview);
                    fabAyuda.setClickable(true);
                    fabAtras.setClickable(true);
                    txtAtras.startAnimation(fabOpen);
                    txtAyuda.startAnimation(fabOpen);
                    txtAtras.setClickable(true);
                    txtAyuda.setClickable(true);
                    isOpen = true;
                }
            }

        });

        //Primera Vez metodo
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if(settings.getBoolean("my_first_time", true)){

            Log.d("PRUEBA", "PRIMERA VEZ");

            firsTimeHelpMode();
            settings.edit().putBoolean("my_first_time",false).commit();
        }else{
            Log.d("PRUEBA", "SEGUNDA VEZ");
        }




    }

    public int positionAction(View view) {
        int position = (int) view.getTag();
        Toast.makeText(view.getContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
        return position;
    }

    public void cargarAdaptador() {

        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Cargando datos...");
        progressDialog1.show();

        // Petición GET
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET, Constantes.GETMisiones, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta Json
                        procesarRespuesta(response);
                        Log.d(TAG, "Response: " + "response");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Log.d(TAG, "Error Volley: " + "error ");
                        Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_LONG).show();
                    }
                }));
    }

    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "articulos" Json
                    JSONArray mensaje = response.getJSONArray("misiongymkh");

                    // Parsear con Gson
                    Mision[] articulos = gson.fromJson(mensaje.toString(), Mision[].class);
                    list = new ArrayList<>(Arrays.asList(articulos));
                    Log.i(TAG, articulos.toString());

                    rca = new CardViewMisiones(this, list, personName, personGivenName, personFamilyName, personEmail, personId);
                    rv.setAdapter(rca);

                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(this, mensaje2, Toast.LENGTH_LONG).show();
                break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
        }

    }

    private void showProgressDialog() {
        if (progressDialog1 == null) {
            progressDialog1 = new ProgressDialog(this);
            progressDialog1.setMessage(getResources().getString(R.string.CerrandoSesion));
            progressDialog1.setIndeterminate(true);

        }

        progressDialog1.show();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        new BackButtonListener().show(fragmentManager, "BackButtonListener");
    }



    private void hideProgressDialog() {
        if (progressDialog1 != null && progressDialog1.isShowing()) {
            progressDialog1.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //TapTargetHelp

    void firsTimeHelpMode(){
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.btnRutas), getString(R.string.TextoAyuda1), getString(R.string.TextoAyuda2))
                        // All options below are optional
                        .outerCircleColor(R.color.AzulClaroJavea)      // Specify a color for the outer circle
                        .targetCircleColor(R.color.naranj2)   // Specify a color for the target circle
                        .titleTextSize(16)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.blanco)      // Specify the color of the title text
                        .descriptionTextSize(16)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.blanco)  // Specify the color of the description text
                        .textColor(R.color.blanco)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.Blacky)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(getResources().getDrawable(R.drawable.play))                     // Specify a custom drawable to draw as the target
                        .targetRadius(30),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional

                    }
                });

    }

}
