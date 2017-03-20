package com.mubeen.vanesa.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.app.AppConfig;
import com.mubeen.vanesa.app.AppController;
import com.mubeen.vanesa.helper.SQLiteHandler;
import com.mubeen.vanesa.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private  static final String TAG = RegisterActivity.class.getSimpleName();
    Button registerButton;
    Button loginButton;

    EditText emailEditText;
    EditText passwordEditText;

    ProgressDialog pDialog;

    SessionManager session;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        if(session.isLoggedin()){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        registerButton = (Button) findViewById(R.id.button_login_register);
        loginButton = (Button) findViewById(R.id.button_login_signin);

        emailEditText = (EditText) findViewById(R.id.editText_login_email);
        passwordEditText = (EditText) findViewById(R.id.editText_login_password);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);




    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.button_login_signin:
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    checkLogin(email,password);
                }
                 break;

            case R.id.button_login_register:
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    private void checkLogin(final String email, final String password) {
        final String tag_string_req = "req_login";

        pDialog.setMessage("Authenticating Username and Password");
        showPDialog();

        StringRequest strRequest = new StringRequest(Request.Method.POST, AppConfig.URL_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(tag_string_req, "Login response: " + response.toString());
                hidePDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String uid = jObj.getString("uid");

                        session.setLogin(true);

                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        db.addUserToDB(username, email, uid, created_at);

                        Intent i = new Intent(LoginActivity.this, Profile.class);
                        startActivity(i);
                        finish();
                    } else {
                        String error_msg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    hidePDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG,"Login error:" + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
                hidePDialog();
                }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest,tag_string_req);

    }

    private void showPDialog(){
        if(!pDialog.isShowing())
        {
            pDialog.show();
        }
    }

    private void hidePDialog(){
        if(pDialog.isShowing())
        {
            pDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
