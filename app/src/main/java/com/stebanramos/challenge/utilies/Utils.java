package com.stebanramos.challenge.utilies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public class Utils {

    private Context context;
    private final String TAG = "Utils";

    public void printtCatch(Exception e, String funcion, String activity) {
        Log.i(activity, funcion);
        e.printStackTrace();
    }

    // Mensaje Sin conexión a internet (Redirecciona hasta ajustes de red ACTION_WIFI_SETTINGS)
    public void Request_Internet(Context cont) {
        Log.i(TAG, "Network_Connected");

        final AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setMessage("Conéctese a una red");
        builder.setTitle("Sin conexión a internet");

        builder.setNeutralButton("Conectar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }
        );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Estado Internet(Conectado a la red (true) sin conexión (false))
    public Boolean Network_Connected(Context ctx) {
        Log.i(TAG, "Network_Connected");

        ConnectivityManager conectionManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
