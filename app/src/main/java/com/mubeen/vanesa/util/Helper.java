package com.mubeen.vanesa.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.activites.MainActivity;
import com.mubeen.vanesa.activites.ShoppingCart;

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
