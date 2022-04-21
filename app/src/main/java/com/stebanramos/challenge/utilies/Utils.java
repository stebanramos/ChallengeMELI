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

    private static final String TAG = "Utils";

    // show exception catch
    public static void printtCatch(Exception e, String funcion, String activity) {
        Log.i(activity, funcion);
        e.printStackTrace();
    }

    // Internet connection message (Redirects to network settings ACTION_WIFI_SETTINGS)
    public static void Request_Internet(Context context) {
        Log.i(TAG, "Network_Connected");

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        } catch (Exception e) {
            printtCatch(e, "Request_Internet", TAG);
        }

    }

    // Internet Status(Connected to the network (true) Offline (false))
    public static Boolean Network_Connected(Context ctx) {
        Log.i(TAG, "Network_Connected");
        boolean isConnected;
        try {
            ConnectivityManager conectionManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isConnected = true;
            } else {
                isConnected = false;
            }
        } catch (Exception e) {
            isConnected = false;
            printtCatch(e, "Network_Connected", TAG);

        }

        return isConnected;
    }
}
