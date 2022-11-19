package com.example.reeadigital;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/** Simple utility class with dialogs, ids, keys etc*/
public class Utils {
    public final static String MOVIE_LIST_API_KEY = "15673ce2e17445aae3ae809f818eb6a3";
    public final static int REQUEST_CODE = 101;
    public final static String ENGLISH_US_LOCALE = "en_us";
    public final static String SPANISH_LOCALE = "es";
    public final static int ENGLISH_US_LOCALE_SPINNER_ID = 0;
    public final static int SPANISH_LOCALE_SPINNER_ID = 1;
    public static AlertDialog showErrorDialog(Context context,
                                             String title,
                                             String message,
                                             DialogInterface.OnClickListener listener
                                             ) {
        return new AlertDialog.Builder(context).
                setTitle(title)
                .setMessage(message)
                .setNeutralButton(context.getString(R.string.retry_button_name),listener)
                .setCancelable(false)
                .create();
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
}
