package com.mubeen.vanesa.Classes;

import java.util.ArrayList;

/**
 * Created by mubeen on 24/02/2017.
 */

public class Product {

    int productID;
    String productName;
    double productPrice;
    String productImageURL;
    String productDescription;
    ArrayList<Product> productsArrayList;


    public String getProductDescription() {
        return productDescription;
    }

    public Product(int productID, String productName, double productPrice, String productImageURL, String ProductDescription) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageURL = productImageURL;
        this.productDescription = ProductDescription;

    }


    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public ArrayList<Product> getProductsArrayList() {
        return this.productsArrayList;
    }



}
