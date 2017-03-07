package com.mubeen.vanesa.Classes;

import java.util.ArrayList;

/**
 * Created by mubeen on 27/02/2017.
 */

public class Cart {


    public static ArrayList<Product> cartList = new ArrayList<>();

    public boolean addProductToCart(Product p){
        if (!findProductInList(p)) {
            this.cartList.add(p);
            return true;
        }else{
            System.out.println("Product already exist");
            return false;
        }
    }
    public void deleteProductFromCart(Product p){
        if (findProductInList(p)) {
            this.cartList.remove(p);
        }else{
            System.out.println("Product does not exist");
        }

    }

    public boolean findProductInList(Product p)
    {
        for(Product theproduct: this.cartList){
            if(theproduct.getProductID() == p.getProductID())
            {
                return true;
            }
        }
        return  false;
    }

    public ArrayList<Product> getallProductsInCart(){
        return this.cartList;
    }
}
