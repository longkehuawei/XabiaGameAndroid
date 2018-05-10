package com.example.informatica.xabiagame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
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
import com.example.informatica.xabiagame.Dialog.OneActionButtonDialogExitoGuardado;
import com.example.informatica.xabiagame.utils.Constantes;
import com.example.informatica.xabiagame.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Informatica on 30/12/2016.
 */

public class DialogGymkhCompletado extends BlurDialogFragment {

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

    private static final String TAG = DialogGymkhCompletado.class.getSimpleName();

    private ProgressDialog progressDialog;
    Button btnGuardadoCompletado;

    TextView tituloM, kmM, duracionM, nivelM;
    String strId, strKm, strTitul, strTiempo;

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;
    String mDescripcion;

    public DialogGymkhCompletado() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogGymkhCompletadoo();
    }

    public AlertDialog createDialogGymkhCompletadoo() {

        Bundle mArgs = getArguments();
        strId = mArgs.getString("id");
        strKm = mArgs.getString("kmtxt");
        strTitul = mArgs.getString("titulstr");
        strTiempo = mArgs.getString("tiempo");
        mDescripcion = mArgs.getString("mDescripcion");
        Log.i(TAG,mDescripcion);

        //Datos Usuario
        personName = mArgs.getString("personName");
        personGivenName = mArgs.getString("personGivenName");
        personFamilyName = mArgs.getString("personFamilyName");
        personEmail = mArgs.getString("personEmail");
        personId = mArgs.getString("personId");

        Log.i(TAG, strId + " " + strKm + " " + strTitul + " " + personId + " " + strTiempo);

        //INFLADOR DIALOGO
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_dialog_mision_terminada, null);
        builder.setView(v);

        btnGuardadoCompletado = (Button)v.findViewById(R.id.btnGuardarMisionCompletada);

        tituloM = (TextView) v.findViewById(R.id.txtTituloCompletado);
        kmM = (TextView) v.findViewById(R.id.txtKMHechos);
        duracionM = (TextView) v.findViewById(R.id.txtTTCompletado);
        nivelM = (TextView) v.findViewById(R.id.txtNivelHecho);

        kmM.setText("No Registrado");
        tituloM.setText(strTitul);
        duracionM.setText(strTiempo);
        nivelM.setText("Bajo");

        btnGuardadoCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarMeta();
            }
        });



        return builder.create();
    }

    public void guardarMeta() {
        /*
        $id,
        $titulo,
        $descripcion,
        $distancia_total,
        $tiempo_total,
        $id_misiongymkh,
        $id_usuario
         */

        // Obtener valores actuales de los controles
        final String titulo = tituloM.getText().toString();
        final String descripcion = mDescripcion;
        Log.i(TAG+"G",mDescripcion);
        final String nivel_dificultad = "bajo";
        final String distancia_total = "0:00";
        final String tiempo_total = strTiempo;
        final String id_misiongymkh = strId;
        final String id_usuario = personId;

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("titulo", titulo);
        map.put("descripcion", descripcion);
        map.put("nivel_dificultad", nivel_dificultad);
        map.put("distancia_total", distancia_total);
        map.put("tiempo_total", tiempo_total);
        map.put("id_misiongymkh", id_misiongymkh);
        map.put("id_usuario", id_usuario);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG+"1", jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT_MISIONES_TERMINADAS,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuesta(response);
                                Bundle bdl = new Bundle();
                                FragmentManager fragmentManager = getFragmentManager();
                                bdl.putString("personName", personName);
                                bdl.putString("personGivenName", personGivenName);
                                bdl.putString("personFamilyName", personFamilyName);
                                bdl.putString("personEmail", personEmail);
                                bdl.putString("personId", personId);
                                DialogFragment newFragment = new OneActionButtonDialogExitoGuardado();
                                newFragment.setArguments(bdl);
                                newFragment.show(fragmentManager, "TAG");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());

                                Bundle bdl = new Bundle();
                                FragmentManager fragmentManager = getFragmentManager();
                                bdl.putString("personName", personName);
                                bdl.putString("personGivenName", personGivenName);
                                bdl.putString("personFamilyName", personFamilyName);
                                bdl.putString("personEmail", personEmail);
                                bdl.putString("personId", personId);
                                DialogFragment newFragment = new OneActionButtonDialogExitoGuardado();
                                newFragment.setArguments(bdl);
                                newFragment.show(fragmentManager, "TAG");

                                /*Bundle bdl = new Bundle();
                                FragmentManager fragmentManager = getFragmentManager();

                                bdl.putString("personName", personName);
                                bdl.putString("personGivenName", personGivenName);
                                bdl.putString("personFamilyName", personFamilyName);
                                bdl.putString("personEmail", personEmail);
                                bdl.putString("personId", personId);

                                DialogFragment newFragment = new OneActionButtonDialog();
                                newFragment.setArguments(bdl);
                                newFragment.show(fragmentManager, "TAG");*/
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );

    }

    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
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