package com.mubeen.vanesa.fragments;

import com.mubeen.vanesa.app.AppConfig;
import com.paypal.base.rest.APIContext;

/**
 * Created by mubeen on 24/03/2017.
 */

public class PaypalHandler
{

        String clientId = AppConfig.PAYPAL_CLIENT_ID;
        String clientSecret = AppConfig.PAYPAL_CLIENT_SECRET;

        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
}
