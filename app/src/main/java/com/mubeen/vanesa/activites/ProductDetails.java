package com.mubeen.vanesa.activites;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    FloatingActionButton addTocart,gotocart;
    CartSharedPrefferences userCart;
    public static ArrayList<Product> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        int productid = getIntent().getExtras().getInt("pid");
        final Product product = ItemFragment.productArrayList.get(productid-1);
        double productPriceDouble = product.getProductPrice();
        productImage= (ImageView)findViewById(R.id.product_details_image);
        productPrice = (TextView)findViewById(R.id.product_details_price);
        productDescription = (TextView)findViewById(R.id.product_details_detail);
        addTocart = (FloatingActionButton) findViewById(R.id.addtocart);
        gotocart = (FloatingActionButton) findViewById(R.id.gotocart);

        Glide.with(this).load(product.getProductImageURL()).into(productImage);

        productPrice.setText(Double.toString(productPriceDouble));
        productDescription.setText(product.getProductDescription());

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userCart == null) {
                    userCart = new CartSharedPrefferences();
                }
                if(userCart.addProductToCart(getApplicationContext(),product)) {
                    new CartSharedPrefferences().updatecartAmount(getApplicationContext(),product,true);
                    Snackbar.make(v, "Product Added to cart", Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(v, "Product already exist in cart", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ShoppingCart.class);
                startActivity(i);
            }
        });
    }
}
