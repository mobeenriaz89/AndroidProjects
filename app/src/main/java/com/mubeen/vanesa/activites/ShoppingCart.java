package com.mubeen.vanesa.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mubeen.vanesa.Classes.Cart;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.model.ProductsCustomList;

public class ShoppingCart extends AppCompatActivity {

    ListView cartList;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartList = (ListView)findViewById(R.id.cartList);
        totalAmount = (TextView) findViewById(R.id.totalamount);

        ProductsCustomList cartlistadapter = new ProductsCustomList(this,this.getApplicationContext(),Cart.cartList);
        cartList.setAdapter(cartlistadapter);

        double Amount = calculateTotalAmount();
        totalAmount.setText(Double.toString(Amount));
    }

    private double calculateTotalAmount() {
        double amount = 0;
        for(int i=0;i<Cart.cartList.size();i++){
            amount += Cart.cartList.get(i).getProductPrice();
        }
        return  amount;
    }
}
