package com.mubeen.vanesa.Classes;

/**
 * Created by mubeen on 29/03/2017.
 */

public class ProductCategory {
    String categoryName;
    String categoryID;


    public ProductCategory(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }
}
