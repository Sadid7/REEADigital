package com.example.reeadigital;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utils {
    final static String apiKey = "15673ce2e17445aae3ae809f818eb6a3";
    final static Integer initialPageNo = 1;
    final static String apiKeyName = "api_key_auth";
    final static String sharedPrefFileName = "com.example.reeadigital.PREFERENCE_FILE_KEY";
    public static AlertDialog showErrorDialog(Context context,
                                             String message,
                                             DialogInterface.OnClickListener listener
                                             ) {
        return new AlertDialog.Builder(context).
                setTitle("Error Occurred").
                setMessage(message)
                .setNeutralButton("Retry",listener).
                        create();
    }

    public static ProgressDialog getProgreesDialog(Context context, String message)
    {
        ProgressDialog progressDialog= new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean isOfflineDataAvailable(Context context, String fileName) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
