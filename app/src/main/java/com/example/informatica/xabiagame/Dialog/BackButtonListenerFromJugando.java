package com.example.informatica.xabiagame.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.informatica.xabiagame.InicioJuegoActivity;
import com.example.informatica.xabiagame.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Informatica on 04/01/2017.
 */

public class BackButtonListenerFromJugando extends BlurDialogFragment {

    private ProgressDialog progressDialog;

    public BackButtonListenerFromJugando() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return BackButtonListenerFromJugando(savedInstanceState);
    }

    public Dialog BackButtonListenerFromJugando(Bundle savedInstanceState) {

        AlertDialog.Builder builder=  new  AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.sisalesseacabaraeljuego))
                .setPositiveButton(getResources().getString(R.string.Salir),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent o = new Intent(getActivity(), InicioJuegoActivity.class);
                                startActivity(o);
                                showProgressDialog();
                            }
                        }
                )
                .setNegativeButton(getResources().getString(R.string.Cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        return builder.create();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getResources().getString(R.string.TerminandoEljuego));
            progressDialog.setIndeterminate(true);

        }

        progressDialog.show();
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
