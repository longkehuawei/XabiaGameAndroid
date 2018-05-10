package com.example.informatica.xabiagame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.informatica.xabiagame.Adapters.MisionesAdapterDefinitiva;
import com.example.informatica.xabiagame.model.Mision;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Informatica on 18/10/2016.
 */

public class DialogMostrarLvMisionSeleccionado extends BlurDialogFragment {

    //BLUR EFFECT

    /**
     * Since image is going to be blurred, we don't care about resolution.
     * Down scale factor to reduce blurring time and memory allocation.
     */
    static final float DEFAULT_BLUR_DOWN_SCALE_FACTOR = 4.0f;

    /**
     * Radius used to blur the background
     */
    static final int DEFAULT_BLUR_RADIUS = 8;

    /**
     * Default dimming policy.
     */
    static final boolean DEFAULT_DIMMING_POLICY = false;

    /**
     * Default debug policy.
     */
    static final boolean DEFAULT_DEBUG_POLICY = false;

    /**
     * Default action bar blurred policy.
     */
    static final boolean DEFAULT_ACTION_BAR_BLUR = false;

    /**
     * Default use of RenderScript.
     */
    static final boolean DEFAULT_USE_RENDERSCRIPT = false;


    private AlertDialog.Builder builder;

    private static final String TAG = DialogMostrarLvMisionSeleccionado.class.getSimpleName();
    String strId;

    Mision art;
    MisionesAdapterDefinitiva<Mision> adapterRuta;

    Gson gson = new Gson();

    TextView actMostrarTit, actMostrarDesc;

    LayoutInflater inflater;
    View v;

    //DATOS
    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    public DialogMostrarLvMisionSeleccionado() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogMostrarLvMisionSeleccionado();
    }

    public AlertDialog createDialogMostrarLvMisionSeleccionado() {
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.activity_mostrar_lvmision_seleccionado, null);

        Bundle mArgs = getArguments();
        strId = mArgs.getString("id");

        personName = mArgs.getString("personName");
        personGivenName = mArgs.getString("personGivenName");
        personFamilyName = mArgs.getString("personFamilyName");
        personEmail = mArgs.getString("personEmail");
        personId = mArgs.getString("personId");

        // Obtención de views

        actMostrarTit = (TextView)v.findViewById(R.id.txtTituloMisionDiaLv);
        actMostrarDesc = (TextView)v.findViewById(R.id.textView17);



        cargarDatos();

        builder.setView(v);
        Button start = (Button)v.findViewById(R.id.btnJugarLvDia);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "A Jugar!", Toast.LENGTH_SHORT).show();

                if (Integer.parseInt(strId) != 0) {

                    Intent i = new Intent(getActivity(), JugandoActivity.class);
                    i.putExtra("id", strId);
                    i.putExtra("personName", personName);
                    i.putExtra("personGivenName", personGivenName);
                    i.putExtra("personFamilyName", personFamilyName);
                    i.putExtra("personEmail", personEmail);
                    i.putExtra("personId", personId);

                    String mDescripcion = art.getDescripcion();
                    i.putExtra("mDescripcion", mDescripcion);
                    Log.i(TAG+"G",mDescripcion);

                    String mTitulo = art.getTitulo();
                    i.putExtra("mTitulo", mTitulo);
                    Log.i(TAG+"G",mTitulo);

                    startActivity(i);
                }
                else
                    Toast.makeText(getActivity(), "Algo malo paso", Toast.LENGTH_SHORT).show();

            }
        });

        return builder.create();
    }

    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GETMisiones_BY_ID + "?id=" + strId;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
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
                    JSONObject object = response.getJSONObject("misiongymkh");

                    //Parsear objeto
                    art = gson.fromJson(object.toString(), Mision.class);

                    actMostrarTit = (TextView)v.findViewById(R.id.txtTituloMisionDiaLv);
                    actMostrarDesc = (TextView)v.findViewById(R.id.textView17);

                    actMostrarTit.setText(art.getTitulo());
                    actMostrarDesc.setText(art.getDescripcion());
                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(getActivity(), mensaje2, Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(getActivity(), mensaje3, Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //BLUR EFFECT

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return (float) 5.0;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        // Enable or disable the use of RenderScript for blurring effect
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDebugEnable() {
        // Enable or disable debug mode.
        // False by default.
        return false;
    }

}
