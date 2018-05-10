package com.example.informatica.xabiagame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.informatica.xabiagame.Dialog.OneActionDesconectado;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Informatica on 05/10/2016.
 */

public class DialogEntrar extends BlurDialogFragment implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = DialogEntrar.class.getSimpleName();
    private SignInButton btnSignInGlg;

    static GoogleApiClient apiClient;
    private static final int RC_SIGN_IN = 1001;

    private ProgressDialog progressDialog;

    private View v;

    //Datos de usuario entrado
    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;
    Uri personPhoto;




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


    public DialogEntrar() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiClient.stopAutoManage((FragmentActivity) getActivity());
        apiClient.disconnect();
    }

    //AL Apretar en jugar, la app automaticamente lee el gmaill y entras..
    /**@Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(apiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }**/

    //Lo que sale una vez apretas el boton de jugar
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getResources().getString(R.string.Entrando));
            progressDialog.setIndeterminate(true);

        }
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    public AlertDialog createLoginDialogo() {

        //ROLLO DE LA API DE GOOGLE SIGN IN
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage((FragmentActivity) getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).addApi(LocationServices.API)
                .build();

        //INFLADOR DIALOGO
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.activity_dialog_entrar, null);
        builder.setView(v);


        //BOTON Desconectarse
        Button desconectarse = (Button) v.findViewById(R.id.btnDialogDesconnect);
        desconectarse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(apiClient.isConnected()) {
                            Log.i(TAG, String.valueOf(apiClient.isConnected()));
                            desconnectUser();
                            Toast.makeText(getActivity(), getResources().getString(R.string.Desconectar), Toast.LENGTH_SHORT).show();
                        }else if(!apiClient.isConnected()){
                            FragmentManager fragmentManager = getFragmentManager();
                            DialogFragment newFragment = new OneActionDesconectado();
                            newFragment.show(fragmentManager, "TAG");
                        }

                    }
                }
        );

        //BOTON CREAR NUEVO USUARIO
        Button btnCrearNewUSU = (Button)v.findViewById(R.id.btnDiaUsuNew);
        btnCrearNewUSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://accounts.google.com/SignUp?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&hl=es"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnSignInGlg = (SignInButton)v.findViewById(R.id.sign_in_button);
        btnSignInGlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //Usuario logueado --> Mostramos sus datos
            GoogleSignInAccount acct = result.getSignInAccount();

            if(acct.getDisplayName() != null){
                personName = acct.getDisplayName();
            }
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();

            Log.i(TAG, personName);
            Log.i(TAG, personGivenName);
            Log.i(TAG, personFamilyName);
            Log.i(TAG, personEmail);
            Log.i(TAG, personId);
            Log.i(TAG, String.valueOf(personPhoto));

            Intent intent = new Intent(getActivity(), InicioJuegoActivity.class);

            intent.putExtra("personName", personName);
            intent.putExtra("personGivenName", personGivenName);
            intent.putExtra("personFamilyName", personFamilyName);
            intent.putExtra("personEmail", personEmail);
            intent.putExtra("personId", personId);
            intent.putExtra("personPhoto", String.valueOf(personPhoto));

            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getActivity(), "Conectado",Toast.LENGTH_SHORT).show();
            hideProgressDialog();

        } else {
            //Usuario no logueado --> Lo mostramos como "Desconectado"
            Toast.makeText(getActivity(), "Desconectado",Toast.LENGTH_SHORT).show();
        }
    }


    public void desconnectUser(){
        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if(apiClient.isConnected()) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.Desconectar), Toast.LENGTH_SHORT).show();
                        }else if(apiClient == null){
                            FragmentManager fragmentManager = getFragmentManager();
                            DialogFragment newFragment = new OneActionDesconectado();
                            newFragment.show(fragmentManager, "TAG");
                        }
                    }
                });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Fallo la conexion con google", Toast.LENGTH_SHORT).show();
    }
}