package com.mubeen.vanesa.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.helper.SQLiteHandler;
import com.mubeen.vanesa.helper.SessionManager;

import java.util.HashMap;

public class Profile extends AppCompatActivity implements  View.OnClickListener {

    SessionManager session;
    SQLiteHandler db;

    TextView usernameTextView;
    Button logout_button;
    Button keepshopping_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        if(!session.isLoggedin()){
            Intent i = new Intent(Profile.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

        usernameTextView = (TextView)findViewById(R.id.textView_profile_username);
        logout_button = (Button)findViewById(R.id.button_profile_logout);
        keepshopping_button = (Button)findViewById(R.id.button_profile_keepshopping);

        HashMap<String,String> allusers = db.getuserDetails();
        String username = allusers.get("name");
        usernameTextView.setText(username);
        logout_button.setOnClickListener(this);
        keepshopping_button.setOnClickListener(this);

    }
    private void logoutuser() {
    session.setLogin(false);
        db.deleteUsers();
        Intent i = new Intent(Profile.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.button_profile_logout:
                logoutuser();
                break;
            case R.id.button_profile_keepshopping:
                finish();
                break;
        }
    }
}
