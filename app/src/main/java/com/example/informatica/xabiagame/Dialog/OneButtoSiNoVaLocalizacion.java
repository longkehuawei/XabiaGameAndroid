package com.example.informatica.xabiagame.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.informatica.xabiagame.InicioJuegoActivity;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Informatica on 07/01/2017.
 */

public class OneButtoSiNoVaLocalizacion extends BlurDialogFragment {

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    public OneButtoSiNoVaLocalizacion() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return OneButtoSiNoVaLocalizacion(savedInstanceState);
    }

    public Dialog OneButtoSiNoVaLocalizacion(Bundle savedInstanceState) {


        //Datos Usuario
        Bundle mArgs = getArguments();
        personName = mArgs.getString("personName");
        personGivenName = mArgs.getString("personGivenName");
        personFamilyName = mArgs.getString("personFamilyName");
        personEmail = mArgs.getString("personEmail");
        personId = mArgs.getString("personId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Error. No disponible la localizacion")
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_info))
                .setMessage("No funciona la localizacion, redireccionando a inicio")
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getActivity(), InicioJuegoActivity.class);
                        intent.putExtra("personName", personName);
                        intent.putExtra("personGivenName", personGivenName);
                        intent.putExtra("personFamilyName", personFamilyName);
                        intent.putExtra("personEmail", personEmail);
                        intent.putExtra("personId", personId);
                        startActivity(intent);
                    }
                });

        return builder.create();
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