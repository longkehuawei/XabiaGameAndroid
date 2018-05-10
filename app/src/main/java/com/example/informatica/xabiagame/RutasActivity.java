package com.example.informatica.xabiagame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.Adapters.CardViewRutas;
import com.example.informatica.xabiagame.model.Ruta;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Informatica on 06/10/2016.
 */

public class RutasActivity extends AppCompatActivity{

    ImageButton btnImgAtras;
    /*ListView lv;
    ArrayList<Ruta> art;
    RutaAdapterDefinitiva<Ruta> adapterRuta;*/

    RecyclerView rv;
    RecyclerView.Adapter rca;
    List<Ruta> list;
    CardView cv;
    private ProgressDialog progressDialog;

    private static final String TAG = "Prueba";
    /*
    Instancia global del FAB
     */
    Gson gson = new Gson();

    public RutasActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        /*lv = (ListView)findViewById(R.id.lvRutas);*/

        rv = (RecyclerView)findViewById(R.id.recyclerViewRutas);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        cv = (CardView)findViewById(R.id.cardViewSelect);

        list = new ArrayList<>();

        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("location_pin.json");
        animationView.loop(true);
        animationView.playAnimation();

        cargarAdaptador();


        /*btnImgAtras = (ImageButton) findViewById(R.id.imgBtnAtras);
        btnImgAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RutasActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });*/

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                //Intent inte = new Intent(RutasActivity.this, ActMostrarRutas.class);
                String algo = "Algo malo ha pasado";
                //view.getContext().startActivity(inte);
                Ruta tareaActual = adapterRuta.getItem(position);
                if (tareaActual.getId() != 0) {
                    Intent intent3 = new Intent(RutasActivity.this, ActMostrarRutas.class);
                    intent3.putExtra("id", String.valueOf(tareaActual.getId()));
                    view.getContext().startActivity(intent3);
                    Log.d(TAG, String.valueOf(tareaActual.getId()));
                }
                else
                    Toast.makeText(RutasActivity.this, algo, Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    public void cargarAdaptador() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos");
        progressDialog.show();

        // Petición GET
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET, Constantes.GETRUTAS, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta Json
                        procesarRespuesta(response);
                        Log.d(TAG, "Response: " + "response");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Volley: " + "error ");
                        progressDialog.dismiss();
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
                    progressDialog.dismiss();
                    // Obtener array "articulos" Json
                    JSONArray mensaje = response.getJSONArray("rutas");

                    //Parsear objeto
                    Ruta[] cr = gson.fromJson(mensaje.toString(), Ruta[].class);
                    list = new ArrayList<>(Arrays.asList(cr));

                    rca = new CardViewRutas(this, list);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RutasActivity.this, MainActivity.class);
        startActivity(i);


    }


}

