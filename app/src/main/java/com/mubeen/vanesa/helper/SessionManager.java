package com.mubeen.vanesa.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by mubeen on 16/03/2017.
 */

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "USERLOGIN";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedin)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,isLoggedin);
        editor.commit();

        Log.d(TAG, "User login session modified");
    }

    public boolean isLoggedin(){
        return pref.getBoolean(KEY_IS_LOGGEDIN,false);
    }
}
