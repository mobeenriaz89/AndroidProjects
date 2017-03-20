package com.mubeen.vanesa.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = RegisterActivity.class.getSimpleName();
    EditText fullnameEditText;
    EditText emailEditText;
    EditText passwordTEditText;
    Button loginButton;
    Button registerButton;

    ProgressDialog pDialog;
    SessionManager session;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameEditText = (EditText) findViewById(R.id.editText_register_fullname);
        emailEditText = (EditText) findViewById(R.id.editText_register_email);
        passwordTEditText = (EditText) findViewById(R.id.editText_register_password);

        loginButton = (Button) findViewById(R.id.button_register_login);
        registerButton = (Button) findViewById(R.id.button_register);


        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        if(session.isLoggedin()){
            Intent i = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.button_register_login:
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.button_register:
                String fullname = fullnameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordTEditText.getText().toString().trim();
                if(!fullname.isEmpty() && !email.isEmpty() && !password.isEmpty())
                {
                registerUser(fullname,email,password);
                }else{
                    Snackbar.make(v,"Please fill all fields", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void registerUser(final String username, final String email, final String password){
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering...");
        showPDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        Log.d(TAG,response);
                        try {
                            JSONObject Jobj = new JSONObject(response);
                            boolean error = Jobj.getBoolean("error");
                            if (!error) {
                                String uid = Jobj.getString("uid");
                                JSONObject user = Jobj.getJSONObject("user");
                                String name = user.getString("name");
                                String email = user.getString("email");
                                String created_at = user.getString("created_at");

                                db.addUserToDB(name, email, uid, created_at);

                                Toast.makeText(getApplicationContext(), "Registered Successfully. Please Login now", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                String errorMsg = Jobj.getString("error_msg");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("tagconvertstr", "["+response+"]");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Registration error:" + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("name", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,tag_string_req);
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
}
