package com.mubeen.vanesa.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.model.CustomCartListAdapter;
import com.mubeen.vanesa.util.CartSharedPrefferences;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    ListView cartList;
    TextView totalAmount;
    CustomCartListAdapter cartlistadapter;
    public static ArrayList cartArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartList = (ListView)findViewById(R.id.cartList);
        totalAmount = (TextView) findViewById(R.id.totalamount);

        cartArrayList = new CartSharedPrefferences().getCartProducts(this);
        if(cartArrayList != null) {
            cartlistadapter = new CustomCartListAdapter(this, cartArrayList);
        }
        cartList.setAdapter(cartlistadapter);
        totalAmount.setText("Rs." + String.valueOf(new CartSharedPrefferences().getCartAmount(this)));

    }


}
