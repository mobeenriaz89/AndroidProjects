package com.mubeen.vanesa.Classes;

public class Product {

    private int productID;
    private String productName;
    private String productPrice;
    private String productImageURL;
    private String productDescription;

    public Product(int productID, String productName, String productPrice, String productImageURL, String ProductDescription) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageURL = productImageURL;
        this.productDescription = ProductDescription;
    }


    public String getProductID() {
        return String.valueOf(productID);
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public String getProductDescription() {
        return productDescription;
    }


}
