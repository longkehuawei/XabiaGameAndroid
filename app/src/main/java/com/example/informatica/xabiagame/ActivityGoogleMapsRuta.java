package com.example.informatica.xabiagame;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.model.Ruta;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityGoogleMapsRuta extends AppCompatActivity implements OnMapReadyCallback {

    //Map Items Para seleccionar tipo de terreno
    private final CharSequence[] MAP_TYPE_ITEMS =
            {"Normal", "Satelite","Terreno", "Hibrido"};

    //Google Maps Inicializado
    private GoogleMap mMap;

    //Info
    String id;
    Gson gson = new Gson();
    ArrayList<Ruta> art;

    //Coordenadas Totales
    String[] coordenadasLatitud;
    String[] coordenadasLongitud;

    private static final String TAG = "Pruebas google maps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_ruta);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //obtener el id desde la activity padre
        id = getIntent().getStringExtra("id");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
           Toast.makeText(getApplication(), "No se pudo obtener ubicacion", Toast.LENGTH_SHORT).show();
        }

        showMapTypeSelectorDialog();
        cargarDatos();
    }

    public void cargarDatos() {
        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GETRUTAS_CON_COORDENADAS_BY_ID + "?id=" + id;

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
                                Log.d(TAG, "Error Volley WIIIII: " + error.getMessage());
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
        PolylineOptions lineOptions = null;
        ArrayList<LatLng> points;
        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");
            points = new ArrayList<>();

            switch (mensaje) {
                case "1":
                    Log.d(TAG, "Procesar respuesta aqui entro, caso 1");
                    // Obtener objeto "meta"

                    lineOptions = new PolylineOptions();

                    JSONArray innerJsonArray = response.getJSONArray(String.valueOf("rutas"));
                    for (int i = 0; i < innerJsonArray.length(); i++) {
                        JSONObject jobj = innerJsonArray.getJSONObject(i);
                        Log.d(TAG, "Procesar respuesta aqui entro, for" + i);

                        coordenadasLatitud = new String[innerJsonArray.length()];
                        coordenadasLongitud = new String[innerJsonArray.length()];

                        for (int j = 0; j < coordenadasLatitud.length; j++){
                            coordenadasLatitud[j] = jobj.getString("coord_latitud");
                        }

                        for (int j = 0; j < coordenadasLongitud.length; j++){
                            coordenadasLongitud[j] = jobj.getString("coord_longitud");
                        }

                        System.out.println(jobj.getString("coord_longitud") + " " + jobj.getString("coord_latitud"));

                        LatLng latlng= new LatLng(Double.parseDouble(jobj.getString("coord_latitud")),Double.parseDouble(jobj.getString("coord_longitud")));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));
                        mMap.addMarker(new MarkerOptions()
                                .title(jobj.getString("nombre"))
                                .snippet(jobj.getString("descripcion"))
                                .position(latlng));
                        points.add(latlng);

                        if(coordenadasLatitud.length < 4){
                            lineOptions.addAll(points);
                            lineOptions.width(10);
                            lineOptions.color(Color.RED);

                            if(lineOptions != null) {
                                mMap.addPolyline(lineOptions);
                            }
                            else {
                                Log.d("onPostExecute","without Polylines drawn");
                            }
                        }
                    }

                    //Parsear objeto
                    Ruta[] cr = gson.fromJson(innerJsonArray.toString(), Ruta[].class);
                    art = new ArrayList<>(Arrays.asList(cr));


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

    //Mostrar Dialogo para elegir el mapa de su agrado
    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = getResources().getString(R.string.Select_Map_Type);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = mMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showMapTypeSelectorDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //ApretarBotonAtras
    @Override
    public void onBackPressed() {

        Intent i = new Intent(ActivityGoogleMapsRuta.this, RutasActivity.class);
        this.finish();
        startActivity(i);
    }

}
