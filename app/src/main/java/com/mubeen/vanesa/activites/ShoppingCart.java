package com.mubeen.vanesa.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.model.CustomCartListAdapter;
import com.mubeen.vanesa.util.CartSharedPrefferences;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    ListView cartList;
    TextView totalAmount;
    CustomCartListAdapter cartlistadapter;
    Button checkoutButton;
    public static ArrayList cartArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartList = (ListView)findViewById(R.id.cartList);
        totalAmount = (TextView) findViewById(R.id.totalamount);
        checkoutButton = (Button) findViewById(R.id.button_checkout);
        cartArrayList = new CartSharedPrefferences().getCartProducts(this);
        if(cartArrayList != null) {
            cartlistadapter = new CustomCartListAdapter(this, cartArrayList);
        }
        cartList.setAdapter(cartlistadapter);
        totalAmount.setText("Rs." + String.valueOf(new CartSharedPrefferences().getCartAmount(this)));
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });
    }

    private boolean checkout() {
        return  false;

    }


}
