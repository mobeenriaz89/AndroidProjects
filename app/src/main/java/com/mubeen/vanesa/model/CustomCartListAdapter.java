package com.mubeen.vanesa.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.util.CartSharedPrefferences;

import java.util.ArrayList;




public class CustomCartListAdapter extends BaseAdapter {

    private Activity mactivity;
    private LayoutInflater inflater;
    private ArrayList<Product>  productsList;


    public CustomCartListAdapter(Activity activity, ArrayList<Product> productsList) {
        this.mactivity = activity;
        this.productsList = productsList;
    }

    @Override
    public int getCount() {
        return this.productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
        inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row,null);
        }


        ImageView productThumbnail = (ImageView) convertView.findViewById(R.id.pImage);
        TextView productName = (TextView) convertView.findViewById(R.id.pTitle);
        TextView productPrice = (TextView) convertView.findViewById(R.id.pPrice);
        ImageButton deleteProduct = (ImageButton) convertView.findViewById(R.id.pDelete);
        if(deleteProduct.getVisibility() == View.GONE){
            deleteProduct.setVisibility(View.VISIBLE);
        }


        final Product p = productsList.get(position);
        productName.setText(p.getProductName());
        Double productprice = Double.parseDouble(p.getProductPrice());
        productPrice.setText("Rs." + productprice.toString());
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CartSharedPrefferences().deleteProductFromCart(mactivity,p);
                productsList.clear();
                productsList = new CartSharedPrefferences().getCartProducts(mactivity);
                notifyDataSetChanged();
                float amount = new CartSharedPrefferences().updatecartAmount(mactivity,p,false);
                TextView totalAmount = (TextView) mactivity.findViewById(R.id.totalamount);
                totalAmount.setText("Rs." + String.valueOf(amount));
            }
        });
        Glide.with(mactivity).
                load(p.getProductImageURL()).
                thumbnail(0.5f).crossFade().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(productThumbnail);
        return convertView;
    }
}
