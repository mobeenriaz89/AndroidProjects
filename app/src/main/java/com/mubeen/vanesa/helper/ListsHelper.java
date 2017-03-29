package com.mubeen.vanesa.helper;

import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.Classes.ProductCategory;

import java.util.ArrayList;

/**
 * Created by mubeen on 29/03/2017.
 */

public class ListsHelper {
    public static ArrayList<ProductCategory> categoryArrayList = new ArrayList<>();
    public static ArrayList<Product> productArrayList = new ArrayList<>();


    public static Product getProductByID(String id){
        for(int i=0; i<ListsHelper.productArrayList.size(); i++) {
            if (productArrayList.get(i).getProductID().equals(id))
            {
                return productArrayList.get(i);
            }
        }
        return null;
    }
}
