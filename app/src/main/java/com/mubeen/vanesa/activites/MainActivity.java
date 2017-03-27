package com.mubeen.vanesa.activites;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.app.AppConfig;
import com.mubeen.vanesa.app.AppController;
import com.mubeen.vanesa.fragments.ItemFragment;
import com.mubeen.vanesa.helper.SQLiteHandler;
import com.mubeen.vanesa.helper.SessionManager;
import com.mubeen.vanesa.util.CartSharedPrefferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener{

    private static final int REQUESTCODE_UPDATE_CART_COUNTER = 1;
    private static final int REQUESTCODE_UPDATE_USER_DETAILS = 2;

    private static final String TAG_FRAGMENT_HOME = "tag_fragment_home";
    ProgressDialog pDialog;

    TextView notifCount;
    static int mNotifCount = 0;
    TextView nav_username;
    TextView nav_email;
    Button button_retry;
    SQLiteHandler db;
    SessionManager session;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Fetching product categories");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().add("Home");
        navigationView.getMenu().getItem(0).setIcon(R.drawable.ic_home_black_24dp);
        session = new SessionManager(getApplicationContext());
        View header = navigationView.getHeaderView(0);
        nav_username = (TextView) header.findViewById(R.id.textView_navheader_username);
        nav_email = (TextView) header.findViewById(R.id.textView_navheader_email);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Profile.class);
                startActivityForResult(i,REQUESTCODE_UPDATE_USER_DETAILS);
            }
        });
        button_retry = (Button) findViewById(R.id.button_retry);

        //detect internet and show the data
        loadData();

        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });



    }

    private void loadData() {
        if(AppConfig.isNetworkStatusAvialable (getApplicationContext())) {
            requestCategoriesFromJson(AppConfig.URL_All_CATEGORIES);
            button_retry.setVisibility(View.GONE);
            getSupportActionBar().show();
            updateNavHeader();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FragmentContainer,new ItemFragment()).commit();
        } else {
            getSupportActionBar().hide();
            button_retry.setVisibility(View.VISIBLE);
            View parentLayout = findViewById(R.id.content_main);
            Snackbar.make(parentLayout, "Please check your Internet Connection", Snackbar.LENGTH_SHORT).show();

        }
    }

    public void updateNavHeader() {

        if(session.isLoggedin()){
            db = new SQLiteHandler(getApplicationContext());
            nav_username.setText(db.getuserDetails().get("name"));
            nav_email.setText(db.getuserDetails().get("email"));
        }else{
            nav_username.setText(R.string.nav_header_default_text);
            nav_email.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        View count = menu.findItem(R.id.action_cart).getActionView();
        View view_toggle_list_grid = menu.findItem(R.id.item_toggle_list_grid).getActionView();
        final Button button_toggle_list_grid = (Button) view_toggle_list_grid.findViewById(R.id.button_toggle_list_grid);
        Button notifButton = (Button) count.findViewById(R.id.notif_button);
        notifCount = (TextView) count.findViewById(R.id.notif_text);
        updateNotifCount();

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ShoppingCart.class);
                startActivityForResult(i,1);
            }
        });
        final int numofcol = session.getColumnCount();

        if(numofcol == 1){
            button_toggle_list_grid.setBackgroundResource(R.drawable.ic_grid_on_white_24dp);
        }else{
            button_toggle_list_grid.setBackgroundResource(R.drawable.ic_view_list_white_24dp);
        }
        button_toggle_list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numofcol == 1){
                    button_toggle_list_grid.setBackgroundResource(R.drawable.ic_view_list_white_24dp);
                    session.setColumnCount(2);
                }else{
                    button_toggle_list_grid.setBackgroundResource(R.drawable.ic_grid_on_white_24dp);
                    session.setColumnCount(1);
                }
                loadData();
            }
        });
        return true;
    }
    public void updateNotifCount(){

        if(new CartSharedPrefferences().getCartProducts(getApplicationContext()) != null)
        mNotifCount = new CartSharedPrefferences().getCartProducts(getApplicationContext()).size();
        notifCount.setText(String.valueOf(mNotifCount));
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = (String) item.getTitle();
        if (title.equals("Root Catalog")) {
            loadData();        }
        else if (id == R.id.nav_profile) {
            Intent i = new Intent(MainActivity.this,Profile.class);
            startActivityForResult(i,REQUESTCODE_UPDATE_USER_DETAILS);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void requestCategoriesFromJson(String url){
        showpDialog();
        navigationView.getMenu().clear();
        JsonArrayRequest productsRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("response", String.valueOf(jsonArray));
                for(int i =0; i<jsonArray.length();i++){
                    try {
                        JSONObject categoryOBJ = (JSONObject) jsonArray.get(i);
                        String name = categoryOBJ.getString("name");
                        navigationView.getMenu().add(name);
                        SubMenu topChannelMenu = navigationView.getMenu().addSubMenu("Top Channels");
                        topChannelMenu.add("Foo");
                        topChannelMenu.add("Bar");
                        topChannelMenu.add("Baz");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hidepDialog();
                navigationView.getMenu().getItem(0).setChecked(true);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("json error:", String.valueOf(volleyError));
                hidepDialog();
            }

        });

        AppController.getInstance().addToRequestQueue(productsRequest);

    }

    @Override
    public void onListFragmentInteraction(Product item) {
        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("pid",Integer.parseInt(item.getProductID()));
        startActivityForResult(i,REQUESTCODE_UPDATE_CART_COUNTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE_UPDATE_CART_COUNTER) {
            updateNotifCount();
        }else if(requestCode == REQUESTCODE_UPDATE_USER_DETAILS){
            updateNavHeader();
        }

    }


    private void hidepDialog() {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void showpDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }


}
