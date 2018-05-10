package com.example.informatica.xabiagame;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.Dialog.BackButtonListenerFromJugando;
import com.example.informatica.xabiagame.Dialog.OneButtoSiNoVaLocalizacion;
import com.example.informatica.xabiagame.model.Mision;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Informatica on 19/10/2016.
 */

public class JugandoActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, SensorEventListener {

    private static final String TAG = "JugandoActivity";

    //Id Pasado
    String id;

    //Jsones
    Mision art;
    ArrayList<Mision> art1;

    String[] titulos;
    String[] pistas;
    String[] coordenadasLatitud;
    String[] coordenadasLongitud;

    Gson gson = new Gson();

    //Textos Pantalla
    TextView titulo, pista, kmTxt;
    ImageView ivRadar;

    //Notificaciones
    private NotificationCompat.Builder ncBuilderStart;
    private NotificationManager nmStart;
    private int notificationStartId;
    private RemoteViews rvsStart;
    private Context context;

    private LocationManager locationManager;
    private LocationListener locationListener;
    String provider;
    Location location;

    private Button btnFinished;
    private TextView txtJuego;

    //Localizacion de la mision
    String latitud;
    String longitud;

    //Localizacion Ahora Mismito
    static Double tvLongitude;
    static Double tvLatitude;

    //Sensor de caminata
    SensorManager sManager;
    Sensor stepSensor;
    private long steps = 0;
    boolean running = false;

    //DATOS
    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;
    String mDescripcion;
    String mTitulo;

    //Cronometro
    Chronometer chronometro;
    String chronoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugando);

        //CARGAMOS DATOS
        id = getIntent().getStringExtra("id");
        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");

        mDescripcion = getIntent().getStringExtra("mDescripcion");
        mTitulo = getIntent().getStringExtra("mTitulo");

        Log.i(TAG+"JUG",mDescripcion);
        Log.i(TAG, personId);
        cargarDatos();

        ivRadar = (ImageView) findViewById(R.id.imgSenyal);

        /*ivRadar.setBackgroundResource(R.drawable.movie);
        AnimationDrawable anim = (AnimationDrawable) ivRadar.getBackground();
        anim.start();*/

        //Texto KM
        kmTxt = (TextView) findViewById(R.id.txtKmJugando);
        kmTxt.setText("0.0");

        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //BOTON FINALIZAMOS
        btnFinished = (Button) findViewById(R.id.btnSiguienteOAcabado);
        btnFinished.setOnClickListener(this);

        //TEXTO LIMPIO
        txtJuego = (TextView) findViewById(R.id.textView21);
        txtJuego.setText("");

        //Notification Start
        /*context = this;
        nmStart = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ncBuilderStart = new NotificationCompat.Builder(this);

        rvsStart = new RemoteViews(getPackageName(), R.layout.custom_notification_start_game);
        rvsStart.setTextViewText(R.id.idNotificacion, "Jugando");
        rvsStart.setProgressBar(R.id.progressBar, 100, 50, true);

        ncBuilderStart.setSmallIcon(R.drawable.logoxabiagame)
                .setAutoCancel(true)
                .setCustomBigContentView(rvsStart)
                .setContentTitle("Jugando a " + mTitulo).setContentText("Dificultad: Bajo");

        nmStart.notify(notificationStartId, ncBuilderStart.build());*/

        //Some locationManager dope shit
        // Getting LocationManager object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Creando objeto Criteria
        Criteria criteria = new Criteria();
        // Cgiendo el nombre del provider que es de criteria
        provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {
            // Get the location from the given provider

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1, this);

            if(location!=null)
                onLocationChanged(location);
            else {
                Toast.makeText(getBaseContext(), "No se ha podido conseguir la localización", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                FragmentManager fragmentManager = getFragmentManager();
                bundle.putString("personName", personName);
                bundle.putString("personGivenName", personGivenName);
                bundle.putString("personFamilyName", personFamilyName);
                bundle.putString("personEmail", personEmail);
                bundle.putString("personId", personId);

                DialogFragment newFragment = new OneButtoSiNoVaLocalizacion();
                newFragment.setArguments(bundle);
                newFragment.show(fragmentManager, "TAG");
            }
        }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

        Log.i(TAG,"Long1 = " + tvLongitude + ", Lat1 = " + tvLatitude);

        //Comprobacion de internet
        if(!isNetworkAvailable()){
            Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show();
        }

        //Cronometro
        chronometro = new Chronometer(this);
        int stoppedMilliseconds = 0;
        chronoText = "00:00";

        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }
        chronometro.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
        chronometro.start();

    }

    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GETPistas_BY_ID + "?id=" + id;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, newURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                                Log.d(TAG, "JSONES RESPUESTA");
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
                    Log.d(TAG, "CASO 1");

                    //Parsear objeto
                    JSONArray innerJsonArray = response.getJSONArray(String.valueOf("misiongymkh"));
                    for (int i = 0; i < innerJsonArray.length(); i++) {
                        JSONObject jobj = innerJsonArray.getJSONObject(i);

                        titulos = new String[innerJsonArray.length()];
                        pistas = new String[innerJsonArray.length()];
                        coordenadasLatitud = new String[innerJsonArray.length()];
                        coordenadasLongitud = new String[innerJsonArray.length()];

                        titulo = (TextView)findViewById(R.id.txtTituloJugando);
                        pista = (TextView)findViewById(R.id.txtPistaJugando);

                        for (int j = 0; j < titulos.length; j++) {
                            titulos[j] = jobj.getString("titulo");
                            Log.i(TAG,"MISION" + titulos[j]);
                            String texto1 = titulos[0];
                            titulo.setText(texto1);
                        }
                        for (int j = 0; j < pistas.length; j++) {
                            pistas[j] = jobj.getString("pista1");
                            Log.i(TAG, "MISION" + pistas[j]);
                            String texto1 = pistas[0];
                            pista.setText(texto1);
                        }

                        String coordlat = "", coordlong = "";
                        for (int j = 0; j < coordenadasLatitud.length; j++) {
                            coordenadasLatitud[j] = jobj.getString("coord_latitud");
                            Log.i(TAG,"MISION" + coordenadasLatitud[j]);
                            coordlat = coordenadasLatitud[0];
                        }
                        for (int j = 0; j < coordenadasLongitud.length; j++) {
                            coordenadasLongitud[j] = jobj.getString("coord_longitud");
                            Log.i(TAG,"MISION" + coordenadasLongitud[j]);
                            coordlong = coordenadasLongitud[0];
                        }
                        latitud = coordlat;
                        longitud = coordlong;
                        Log.i(TAG,"Mision2 " + coordlat + " " + coordlong);
                        Log.i(TAG,"Mision2 " + latitud + " " + longitud);
                    }

                    //Parsear objeto
                    Mision[] cr = gson.fromJson(innerJsonArray.toString(), Mision[].class);
                    art1 = new ArrayList<>(Arrays.asList(cr));

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

    @Override
    public void onLocationChanged(Location location) {
        // Setting Current Longitude
        tvLongitude = location.getLongitude();
        // Setting Current Latitude
        tvLatitude = location.getLatitude();
        Log.i(TAG,"onLocationChanged" +"Long = " + tvLongitude + ", Lat = " + tvLatitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {Log.d("Latitude","disable");}

    @Override
    public void onProviderEnabled(String provider) {Log.d("Latitude","enable");}

    @Override
    public void onProviderDisabled(String provider) {Log.d("Latitude","status");}

    @Override
    public void onClick(View v) {
        if(v.equals(btnFinished)) {
            //Formula haversine igual a kilometros totales
            if (haversineFormula() > 0 && haversineFormula() < 0.0500) {
                //Crono
                chronometro.stop();
                showElapsedTime();
                //Toast Encontrao
                Toast.makeText(getApplicationContext(), "ENCONTRADO", Toast.LENGTH_SHORT).show();
                //Vibrador
                Vibrator c = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 1000 milliseconds
                c.vibrate(1000);
                //Texto aleatorio
                String[] encontrado = {getResources().getString(R.string.Enhorabuenalohasencontrado)};
                int random = (int) (Math.random() * encontrado.length);
                txtJuego.setText(encontrado[random]);
                //Wifi
                Resources res = getResources(); /** from an Activity */
                ivRadar.setImageDrawable(res.getDrawable(R.drawable.wifi));
                //Steps
                sManager.unregisterListener(this, stepSensor);
                kmTxt.setText(String.valueOf(getDistanceRun(steps)));

                //Get KM Que quedan
                Toast.makeText(getApplicationContext(), haversineFormula().toString().substring(0,1) + "Km "
                        + haversineFormula().toString().substring(2,4) + "Metros", Toast.LENGTH_SHORT).show();


                //Pasar datos
                Bundle bundle = new Bundle();
                FragmentManager fragmentManager = getFragmentManager();
                bundle.putString("id", String.valueOf(id));
                bundle.putString("kmtxt", kmTxt.getText().toString());
                bundle.putString("titulstr", titulo.getText().toString());
                bundle.putString("tiempo", chronoText);
                bundle.putString("mDescripcion", mDescripcion);

                bundle.putString("personName", personName);
                bundle.putString("personGivenName", personGivenName);
                bundle.putString("personFamilyName", personFamilyName);
                bundle.putString("personEmail", personEmail);
                bundle.putString("personId", personId);
                DialogFragment newFragment = new DialogGymkhCompletado();
                newFragment.setArguments(bundle);
                newFragment.show(fragmentManager, "TAG");
            }
            if (haversineFormula() > 0.0500 && haversineFormula() < 0.1000) {
                //Muestra tiempo total
                showElapsedTime();
                //Random frase aleatoria
                String[] estascerca = {getResources().getString(R.string.Estascerca), getResources().getString(R.string.Bastantecercavaya), getResources().getString(R.string.Alomejorestadetras), getResources().getString(R.string.Ysiestadetras), getResources().getString(R.string.Ysieseseedificio)};
                int random = (int) (Math.random() * estascerca.length);
                txtJuego.setText(estascerca[random]);
                //wifi
                Resources res = getResources(); /** from an Activity */
                ivRadar.setImageDrawable(res.getDrawable(R.drawable.wifidos));
                kmTxt.setText(String.valueOf(getDistanceRun(steps)));

                //Get KM Que quedan
                Toast.makeText(getApplicationContext(), haversineFormula().toString().substring(0,1) + "Km "
                        + haversineFormula().toString().substring(2,4) + "Metros", Toast.LENGTH_SHORT).show();

            } else if (haversineFormula() > 0.1001) {
                showElapsedTime();
                Toast.makeText(getApplicationContext(), "Sigue Buscando", Toast.LENGTH_SHORT).show();

                String[] sigueBuscando = {getResources().getString(R.string.Siguebuscando),
                        getResources().getString(R.string.Estaslejostranquilo), getResources().getString(R.string.Meaburro),
                        getResources().getString(R.string.Yquetedireyo), getResources().getString(R.string.Vaunultimoesfuerzo)};

                int random = (int) (Math.random() * sigueBuscando.length);
                txtJuego.setText(sigueBuscando[random]);

                Resources res = getResources(); /** from an Activity */
                ivRadar.setImageDrawable(res.getDrawable(R.drawable.wifitres));
                /*kmTxt.setText(String.valueOf(getDistanceRun(steps)));*/

                //Get KM Que quedan
                Toast.makeText(getApplicationContext(), haversineFormula().toString().substring(0,1) + "Km "
                        + haversineFormula().toString().substring(2,5) + "Metros", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //COGER LA DISTANCIA CON OBJETIVO
    Double haversineFormula(){
        Double distance = 0.0;
        if((latitud!=null) && (longitud!=null)) {
            Double longg, latt;

            Log.i(TAG,"MovilHaversione " + tvLatitude + " " + tvLongitude);

            //Latitud y longitud de la ubicacion del telefono
            String longggg = tvLatitude.toString();
            String latttt = tvLatitude.toString();

            //Meter 11 caracteres siempre, ubicacion del objetivo
            longg = Double.parseDouble(longitud.substring(0, longggg.length()-6));
            latt = Double.parseDouble(latitud.substring(0, latttt.length()-6));

            Log.i(TAG,"Mision sin convertir" + latitud + " " + longitud);
            Log.i(TAG,"MOVIL " + tvLatitude + " " + tvLongitude);
            Log.i(TAG,"Mision " + latt + " " + longg);

            // TODO Auto-generated method stub
            final int R = 6371; // Radious of the earth
            Double lat1 = tvLatitude;
            Double lon1 = tvLongitude;
            Double lat2 = latt;
            Double lon2 = longg;
            Double latDistance = toRad(lat2 - lat1);
            Double lonDistance = toRad(lon2 - lon1);
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            distance = R * c;

            System.out.println("The distance between two lat and long is::" + distance);
            //0.32871104213186997
            /*int km = 10;
            int doubleDis = distance.intValue();
            if(doubleDis>= km){
                //Get KM Que quedan
                Toast.makeText(getApplicationContext(), haversineFormula().toString().substring(0,2) + "Km "
                        + haversineFormula().toString().substring(3,6) + "Metros", Toast.LENGTH_SHORT).show();
            }else{
                //Get KM Que quedan
                Toast.makeText(getApplicationContext(), haversineFormula().toString().substring(0,1) + "Km "
                        + haversineFormula().toString().substring(2,5) + "Metros", Toast.LENGTH_SHORT).show();
            }*/

        }else{
            Bundle bundle = new Bundle();
            FragmentManager fragmentManager = getFragmentManager();
            bundle.putString("personName", personName);
            bundle.putString("personGivenName", personGivenName);
            bundle.putString("personFamilyName", personFamilyName);
            bundle.putString("personEmail", personEmail);
            bundle.putString("personId", personId);
            DialogFragment newFragment = new OneButtoSiNoVaLocalizacion();
            newFragment.setArguments(bundle);
            newFragment.show(fragmentManager, "TAG");
        }
        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    //SENSOR MANAGER
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(stepSensor != null){
            sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(getApplicationContext(), "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            kmTxt.setText(String.valueOf(event.values[0]));
        }
    }

    //Distancia Andada
    //function to determine the distance run in kilometers using average step length for men and number of steps
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    //ApretarBotonAtras
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        new BackButtonListenerFromJugando().show(fragmentManager, "BackButtonListenerFromJugando");

        Intent i = new Intent(JugandoActivity.this, InicioJuegoActivity.class);
        i.putExtra("personName", personName);
        i.putExtra("personGivenName", personGivenName);
        i.putExtra("personFamilyName", personFamilyName);
        i.putExtra("personEmail", personEmail);
        i.putExtra("personId", personId);

        startActivity(i);
    }

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometro.getBase();
        int seconds = (int) (elapsedMillis / 1000) % 60 ;
        int minutes = (int) ((elapsedMillis / (1000*60)) % 60);
        int hours   = (int) ((elapsedMillis / (1000*60*60)) % 24);
        chronoText = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
        Log.i(TAG,chronoText);
    }

}
