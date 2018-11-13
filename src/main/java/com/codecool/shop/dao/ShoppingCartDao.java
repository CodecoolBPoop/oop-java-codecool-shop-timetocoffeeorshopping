package com.codecool.shop.dao;

import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Product;

public interface ShoppingCartDao {

    void addItem(String user, Product product, int count);
    void removeItem(String user, Product product);
    void cartProcessed(String user);
    ShoppingCart getUserCart(String user);
}
