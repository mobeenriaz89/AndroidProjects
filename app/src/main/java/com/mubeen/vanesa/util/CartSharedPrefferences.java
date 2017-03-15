package com.mubeen.vanesa.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.activites.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mubeen on 10/03/2017.
 */

public class CartSharedPrefferences {
    public static final String PREF_Name = "SHOPPING_CART";
    public static final String PRODUCTS_LIST = "PRODUCTS_LIST";
    public static final String CART_AMOUNT = "CART_AMOUNT";

    private static  CartSharedPrefferences instance;

    public static CartSharedPrefferences getInstance() {
        return instance;
    }



    public float updatecartAmount(Context context,Product product,boolean isAdd){
        Float cartAmount = getCartAmount(context);
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = context.getSharedPreferences(PREF_Name,Context.MODE_PRIVATE);
        editor = prefs.edit();
        if(isAdd) {
            cartAmount += (float) product.getProductPrice();
        }else{

            cartAmount -= (float) product.getProductPrice();

        }
        if(cartAmount >= 0) {
            editor.putFloat(CART_AMOUNT, cartAmount);
        }
        editor.commit();
        return getCartAmount(context);
    }

    public float getCartAmount(Context context){
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREF_Name,Context.MODE_PRIVATE);
        return prefs.getFloat(CART_AMOUNT,0);
    }

    public void saveCartProducts(Context context, ArrayList<Product> cartList){
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = context.getSharedPreferences(PREF_Name, Context.MODE_PRIVATE);
        editor = prefs.edit();

        Gson gson = new Gson();
        String cartProductsJSON = gson.toJson(cartList);
        editor.putString(PRODUCTS_LIST, cartProductsJSON);
        editor.commit();

    }

    public ArrayList<Product> getCartProducts(Context context){
        SharedPreferences prefs;
        List<Product> cartProducts;
        prefs = context.getSharedPreferences(PREF_Name,Context.MODE_PRIVATE);
        if(prefs.contains(PRODUCTS_LIST)){
            String JSONString = prefs.getString(PRODUCTS_LIST,null);
            Gson gson = new Gson();
            Product [] productsArray = gson.fromJson(JSONString, Product[] .class);
            cartProducts = Arrays.asList(productsArray);
            cartProducts = new ArrayList<>(cartProducts);

        }else
            return null;

        return (ArrayList<Product>) cartProducts;

    }

    public boolean addProductToCart(Context context, Product product){
        ArrayList<Product> productsList = getCartProducts(context);
        if (productsList == null)
            productsList = new ArrayList<Product>();
        if(!Exist(productsList,product)){
            productsList.add(product);
            saveCartProducts(context, productsList);

            return true;
        }
        return false;
    }

    private boolean Exist(List<Product> productsList, Product product) {
        for(int i = 0; i<productsList.size(); i++){
            if(productsList.get(i).getProductID() == product.getProductID()){
                return true;
            }
        }
        return false;
    }


    public boolean deleteProductFromCart(Context context, Product product){
        ArrayList<Product> productsList = getCartProducts(context);

            int id = Integer.parseInt(product.getProductID());
            for(int i=0;i<productsList.size();i++) {
                if(productsList.get(i).getProductID() == product.getProductID()) {
                    productsList.remove(i);
                    saveCartProducts(context, productsList);
                    return true;
                }
            }


        return false;
    }

}
