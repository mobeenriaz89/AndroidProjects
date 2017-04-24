package com.mubeen.vanesa.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

/**
 * Created by mubeen on 16/03/2017.
 */

public class AppConfig {
    public static final String URL_login = "http://production.technology-architects.com/vanesa/android_login_api/login.php";
    public static final String URL_register = "http://production.technology-architects.com/vanesa/android_login_api/register.php";
    public static final String URL_All_Products = "http://production.technology-architects.com/vanesa/testapi.php";
    public static final String URL_All_CATEGORIES = "http://production.technology-architects.com/vanesa/testapicategories.php";


    public static final int CATEGORY_ID_ROOT_CATALOG = 0;

    public static final String KEY_CATEGORY_ID = "categoryid";

    // PayPal app configuration
    public static final String PAYPAL_CLIENT_ID = "AdXpfPctAvZUqFj6O_PQjpbEesN8_llejpXx5fJTi1SF8t2y2ecvYw_DwVHhzBhvV2cVQIElxJeYhrK1";
    public static final String PAYPAL_CLIENT_SECRET = "EOgoyC1q3RfKgHYqdp47kX9imnziyVVZhFPCvP60O_bB_DKFLRP50pjQZsjK0XBijhGYe4tgWl_Yli-I";

    public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    public static final String DEFAULT_CURRENCY = "USD";



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
