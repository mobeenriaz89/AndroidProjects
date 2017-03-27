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
    public static final String URL_All_CATEGORIES = "http://production.technology-architects.com/vanesa/testapicategories.php";

    public static final String PAYPAL_CLIENT_ID = "AR5n0W81LZsNgZ8IpvJbqt8IH8c1Kos1pqJ4RSdrL_ViVNnnuDMNWtuw4mcnlx2YXOge4oW9py9NvLoU";
    public static final String PAYPAL_CLIENT_SECRET = "EDVOnJiC18uPY3VGO2nO7mvJK86mXj05Kojs_BmS8tZZtaaTuXyS_vv4g1liSksQ97cqkGq4wanVGipq";

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
