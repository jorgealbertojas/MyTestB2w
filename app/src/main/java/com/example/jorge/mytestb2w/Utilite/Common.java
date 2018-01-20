package com.example.jorge.mytestb2w.Utilite;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jorge on 19/01/2018.
 * Common for all project.
 */

public class Common {

    /**
     * Checks if internet is ok .
     */
    public static boolean isOnline(Object object) {
        ConnectivityManager cm =
                (ConnectivityManager) object;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
