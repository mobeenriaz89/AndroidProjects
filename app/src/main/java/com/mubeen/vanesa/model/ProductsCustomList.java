package com.mubeen.vanesa.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;

import java.util.ArrayList;

/**
 * Created by mubeen on 24/02/2017.
 */

public class ProductsCustomList extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Product>  productsList;


    public ProductsCustomList(Activity activity, Context mContext, ArrayList<Product> productsList) {
        this.activity = activity;
        this.mContext = mContext;
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
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row,null);
        }


        ImageView productThumbnail = (ImageView) convertView.findViewById(R.id.pImage);
        TextView productName = (TextView) convertView.findViewById(R.id.pTitle);
        TextView productPrice = (TextView) convertView.findViewById(R.id.pPrice);

        Product p = productsList.get(position);
        productName.setText(p.getProductName());
        Double productprice =p.getProductPrice();
        productPrice.setText(productprice.toString());

        Glide.with(mContext).
                load(p.getProductImageURL()).
                thumbnail(0.5f).crossFade().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(productThumbnail);
        return convertView;
    }
}
