package com.mubeen.vanesa.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mubeen on 16/03/2017.
 */

public class AppConfig {
    public static final String URL_login = "http://production.technology-architects.com/vanesa/android_login_api/login.php";
    public static final String URL_register = "http://production.technology-architects.com/vanesa/android_login_api/register.php";
    public static final String URL_All_Products = "http://production.technology-architects.com/vanesa/testapi.php";

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }
}
