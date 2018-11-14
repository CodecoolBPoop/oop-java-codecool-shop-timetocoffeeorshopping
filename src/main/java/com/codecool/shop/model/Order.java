package com.codecool.shop.model;

import java.util.List;

public class Order {

    private static int id = 0;
    private boolean isItActive;
    private boolean isItPayed;
    private static List<Order>;
    private ShoppingCart shoppingCart;

    /*
    we have to add customer as parameter
     */
    public Order(ShoppingCart shoppingCart) {
        id += 1;
        this.shoppingCart = shoppingCart;
    }

    public void setItActive(boolean itActive) {
        isItActive = itActive;
    }

    public void setItPayed(boolean itPayed) {
        isItPayed = itPayed;
    }
}
