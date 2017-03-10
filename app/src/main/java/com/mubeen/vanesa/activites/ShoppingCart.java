package com.mubeen.vanesa.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.model.CustomCartListAdapter;
import com.mubeen.vanesa.util.CartSharedPrefferences;

public class ShoppingCart extends AppCompatActivity {

    public static ListView cartList;
    public static TextView totalAmount;
    public static CustomCartListAdapter cartlistadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartList = (ListView)findViewById(R.id.cartList);
        totalAmount = (TextView) findViewById(R.id.totalamount);

        cartlistadapter = new CustomCartListAdapter(this,new CartSharedPrefferences().getCartProducts(this));
        cartList.setAdapter(cartlistadapter);
        totalAmount.setText(String.valueOf(new CartSharedPrefferences().getCartAmount(this)));

    }


}
