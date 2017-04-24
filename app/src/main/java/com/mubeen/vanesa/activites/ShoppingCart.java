package com.mubeen.vanesa.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.model.CustomCartListAdapter;
import com.mubeen.vanesa.util.CartSharedPrefferences;


import java.math.BigDecimal;
import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    ListView cartList;
    TextView totalAmountTextView;
    CustomCartListAdapter cartlistadapter;
    Button checkoutButton;
    public static ArrayList cartArrayList;
    BigDecimal finalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartList = (ListView)findViewById(R.id.cartList);
        totalAmountTextView = (TextView) findViewById(R.id.totalamount);
        checkoutButton = (Button) findViewById(R.id.button_checkout);
        cartArrayList = new CartSharedPrefferences().getCartProducts(this);
        if(cartArrayList != null) {
            cartlistadapter = new CustomCartListAdapter(this, cartArrayList);
        }
        cartList.setAdapter(cartlistadapter);
        finalAmount = BigDecimal.valueOf(new CartSharedPrefferences().getCartAmount(this));
        totalAmountTextView.setText("Rs." + String.valueOf(finalAmount));
        if(new CartSharedPrefferences().getCartProducts(this).isEmpty()) {
            checkoutButton.setEnabled(false);
        }else{
            checkoutButton.setEnabled(true);

        }
            checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShoppingCart.this,MyPaymentActivity.class);
                startActivity(i);
            }
        });
    }


}
