package com.example.informatica.xabiagame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.Adapters.CardViewMisionesCompletadas;
import com.example.informatica.xabiagame.model.Misiones_Compl;
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
 * Created by Informatica on 04/01/2017.
 */

public class ActFragGymksCompletados  extends Activity {

    /**
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     * return inflater.inflate(R.layout.activity_mi_perfil, container, false);
     * }
     **/

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    /*ListView lv;
    ArrayList<Misiones_Compl> art;
    MisionesCompletadasDefinitiva<Misiones_Compl> adapterMisionCompl;*/

    RecyclerView rv;
    RecyclerView.Adapter rca;
    List<Misiones_Compl> list;
    private ProgressDialog progressDialog;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_gymkh_completados);

        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");

        //lv = (ListView)findViewById(R.id.lvMisionesCompl);

        rv = (RecyclerView)findViewById(R.id.recyclerViewCompletado);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        cargarDatos();


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActFragGymksCompletados.this, InicioJuegoActivity.class);
        i.putExtra("personName", personName);
        i.putExtra("personGivenName", personGivenName);
        i.putExtra("personFamilyName", personFamilyName);
        i.putExtra("personEmail", personEmail);
        i.putExtra("personId", personId);

        startActivity(i);
    }

    public void cargarDatos() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_MISIONES_COMPLETADAS + "?id=" + personId;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, newURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                                Log.d("YO", "JSONES RESPUESTA");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.d("YO", "Error Volley: " + error.getMessage());
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

                    progressDialog.dismiss();
                    //Parsear objeto

                    JSONArray innerJsonArray = response.getJSONArray(String.valueOf("misiongymkhcompl"));

                    //Parsear objeto
                    Misiones_Compl[] cr = gson.fromJson(innerJsonArray.toString(), Misiones_Compl[].class);
                    list = new ArrayList<>(Arrays.asList(cr));

                    rca = new CardViewMisionesCompletadas(this, list);
                    rv.setAdapter(rca);
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