package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;

import java.util.List;

public class Order {

    private static int id = 0;
    private boolean isActive;
    private boolean isPayed;
    private ShoppingCart shoppingCart;
    private String user;


    public Order() {
        id += 1;
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


    public boolean getIsActive() {
        return isActive;
    }

    public void isProcessed() {
        this.isActive = false;
    }

    public void setIsPayed(boolean itPayed) {
        isPayed = isPayed;
    }
}
