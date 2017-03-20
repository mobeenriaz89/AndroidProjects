package com.mubeen.vanesa.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mubeen.vanesa.Classes.Product;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.fragments.ItemFragment.OnListFragmentInteractionListener;

import java.text.DecimalFormat;
import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    public MyItemRecyclerViewAdapter(List<Product> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(mContext).load(holder.mItem.getProductImageURL()).into(holder.productImage);
        Float price =  Float.parseFloat(mValues.get(position).getProductPrice());
        holder.productPrice.setText("Rs." + String.valueOf(price));
        holder.productName.setText(mValues.get(position).getProductName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;

        private Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            productImage = (ImageView) view.findViewById(R.id.pImage);
            productName = (TextView) view.findViewById(R.id.pTitle);
            productPrice = (TextView) view.findViewById(R.id.pPrice);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText() + "'";
        }
    }
}
