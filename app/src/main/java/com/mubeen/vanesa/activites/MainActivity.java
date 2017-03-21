package com.mubeen.vanesa.activites;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.app.AppConfig;
import com.mubeen.vanesa.fragments.ItemFragment;
import com.mubeen.vanesa.helper.SQLiteHandler;
import com.mubeen.vanesa.helper.SessionManager;
import com.mubeen.vanesa.util.CartSharedPrefferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener{

    private static final int REQUESTCODE_UPDATE_CART_COUNTER = 1;
    private static final int REQUESTCODE_UPDATE_USER_DETAILS = 2;

    private static final String TAG_FRAGMENT_HOME = "tag_fragment_home";

    TextView notifCount;
    static int mNotifCount = 0;
    TextView nav_username;
    TextView nav_email;

    SQLiteHandler db;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        updateNavHeader();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FragmentContainer,new ItemFragment()).commit();

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
            button_toggle_list_grid.setBackgroundResource(R.drawable.grid_layout);
        }else{
            button_toggle_list_grid.setBackgroundResource(R.drawable.list_layout);
        }
        button_toggle_list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numofcol == 1){
                    button_toggle_list_grid.setBackgroundResource(R.drawable.list_layout);
                    session.setColumnCount(2);
                }else{
                    button_toggle_list_grid.setBackgroundResource(R.drawable.grid_layout);
                    session.setColumnCount(1);
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FragmentContainer,new ItemFragment()).commit();
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            ft.replace(R.id.FragmentContainer,new ItemFragment(),TAG_FRAGMENT_HOME).commit();
        }
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


}
