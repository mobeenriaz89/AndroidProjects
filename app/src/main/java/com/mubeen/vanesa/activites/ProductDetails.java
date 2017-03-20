package com.mubeen.vanesa.activites;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.fragments.ItemFragment;
import com.mubeen.vanesa.util.CartSharedPrefferences;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity{


    ImageView productImage;
    TextView productPrice;
    TextView productDescription;
    FloatingActionButton addTocart;
    CartSharedPrefferences userCart;
    TextView notifCount;
    int mNotifCount;
    Boolean isProductAdded = false;
    public static ArrayList<Product> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        int productid = getIntent().getExtras().getInt("pid");
        final Product product = ItemFragment.productArrayList.get(productid-1);
        setTitle(product.getProductName().toUpperCase());

        productImage= (ImageView)findViewById(R.id.product_details_image);
        productPrice = (TextView)findViewById(R.id.product_details_price);
        productDescription = (TextView)findViewById(R.id.product_details_detail);
        addTocart = (FloatingActionButton) findViewById(R.id.addtocart);

        Glide.with(this).load(product.getProductImageURL()).into(productImage);

        Float price = Float.valueOf(product.getProductPrice());
        productPrice.setText("Rs." + String.valueOf(price));
        productDescription.setText(product.getProductDescription());

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userCart == null) {
                    userCart = new CartSharedPrefferences();
                }
                if(userCart.addProductToCart(getApplicationContext(),product)) {
                    setNotifCount();
                    isProductAdded = true;
                    new CartSharedPrefferences().updatecartAmount(getApplicationContext(),product,true);
                    Snackbar.make(v, "Product Added to cart", Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(v, "Product already exist in cart", Snackbar.LENGTH_SHORT).show();

                }
            }
        });




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        View count = menu.findItem(R.id.action_cart).getActionView();
        Button notifButton = (Button) count.findViewById(R.id.notif_button);
        notifCount = (TextView) count.findViewById(R.id.notif_text);
        setNotifCount();

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetails.this,ShoppingCart.class);
                startActivityForResult(i,1);
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setNotifCount();
    }

    public void setNotifCount(){

        if(new CartSharedPrefferences().getCartProducts(getApplicationContext()) != null)
            mNotifCount = new CartSharedPrefferences().getCartProducts(getApplicationContext()).size();
        notifCount.setText(String.valueOf(mNotifCount));
    }
}
