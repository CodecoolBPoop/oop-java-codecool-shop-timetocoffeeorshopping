package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;

import java.util.List;

public class Order {

//    private static int id = 0;

    private boolean isActive;
    private boolean isPayed;
    private ShoppingCart shoppingCart;
    private Customer customer;
    private String user;

    /*
    Until DaoMEM exist
     */

    public Order() {
    }

    public Order(Customer customer, ShoppingCart shoppingCart) {
        this.customer = customer;
        this.shoppingCart = shoppingCart;
//        id += 1;
    }

    public ShoppingCart setShoppingCart(String user) {
        shoppingCart = ShoppingCartDaoMem.getInstance().getUserCart(user);
        return shoppingCart;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void isProcessed() {
        this.isActive = false;
        shoppingCart.isProcessed();
    }

    public void setIsPayed(boolean itPayed) {
        isPayed = isPayed;
    }
}
