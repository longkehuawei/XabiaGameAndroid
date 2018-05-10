package com.example.informatica.xabiagame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.model.Ruta;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Informatica on 13/10/2016.
 */

public class ActMostrarRutas extends AppCompatActivity {

    private Button btnGM;
    private static final String TAG = "PRUEBA";

    String id;

    TextView actMostrarTit, actMostrarDesc, actMostrarDificultad, actMostrarKM, actMostrarElev;

    Ruta art;
    Gson gson = new Gson();

    ImageView imgRuta;
    Bitmap bm;
    String fotoCreada = "foto";

    static int resID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lv_seleccionado);

        //obtener el id desde la activity padre
        id = getIntent().getStringExtra("id");

        Log.d(TAG, String.valueOf(id));

        // Obtención de views

        actMostrarTit = (TextView)findViewById(R.id.txtTituloRutaLvVerRutas);
        actMostrarDesc = (TextView)findViewById(R.id.txtDescRutaLvVerRutas);
        actMostrarDificultad = (TextView)findViewById(R.id.txtDificultadRutaLvVerRutas);
        actMostrarKM = (TextView)findViewById(R.id.txtKilometrosRutaLvVerRutas);
        actMostrarElev = (TextView)findViewById(R.id.txtElevacionRutaLvVerRutas);

        cargarDatos();

        btnGM = (Button) findViewById(R.id.btnVerGlgMaps);
        btnGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActMostrarRutas.this, ActivityGoogleMapsRuta.class);
                i.putExtra("id", String.valueOf(id));
                startActivity(i);
                ActMostrarRutas.this.finish();
            }
        });

        //COMPROBACION DE IMAGEN
        fotoCreada = fotoCreada.concat(id);
        imgRuta = (ImageView) findViewById(R.id.imageView7);
        int checkExistence = getResources().getIdentifier(fotoCreada, "drawable", getPackageName());

        if ( checkExistence != 0 ) {  // the resouce exists...
            int resID = getResources().getIdentifier(fotoCreada , "drawable", getPackageName());
            bm = BitmapFactory.decodeResource(getResources(), resID);
            imgRuta.setImageBitmap(bm);
        }
        else {  // checkExistence == 0  // the resouce does NOT exist!!
            String noAvailable = "no_image_available";
            int resIDADA = getResources().getIdentifier(noAvailable , "drawable", getPackageName());
            bm = BitmapFactory.decodeResource(getResources(), resIDADA);
            imgRuta.setImageBitmap(bm);
        }


    }

    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GETRUTAS_BY_ID + "?id=" + id;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, newURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                 Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }
    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "meta"
                    JSONObject object = response.getJSONObject("rutas");

                    //Parsear objeto
                    art = gson.fromJson(object.toString(), Ruta.class);

                    actMostrarTit = (TextView)findViewById(R.id.txtTituloRutaLvVerRutas);
                    actMostrarDesc = (TextView)findViewById(R.id.txtDescRutaLvVerRutas);
                    actMostrarDificultad = (TextView)findViewById(R.id.txtDificultadRutaLvVerRutas);
                    actMostrarKM = (TextView)findViewById(R.id.txtKilometrosRutaLvVerRutas);
                    actMostrarElev = (TextView)findViewById(R.id.txtElevacionRutaLvVerRutas);

                    actMostrarTit.setText(art.getNombre());
                    actMostrarDesc.setText(art.getDescripcion());
                    actMostrarDificultad.setText(art.getNivel_dificultad());
                    actMostrarKM.setText(String.valueOf(art.getDistancia()));
                    actMostrarElev.setText(String.valueOf(art.getDesnivel_acumulado_ascenso()));
                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(this, mensaje2, Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(this, mensaje3, Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

