package com.mubeen.vanesa.util;

import android.app.Activity;


/**
 * Created by mubeen on 15/03/2017.
 */

public class Helper {

    private static Helper instance;

    public static Helper getInstance() {
        return instance;
    }

    public void invalidateActionBar(Activity activity){
        activity.invalidateOptionsMenu();
    }


}
