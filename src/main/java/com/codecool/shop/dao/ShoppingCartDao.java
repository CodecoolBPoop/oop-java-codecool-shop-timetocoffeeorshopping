package com.codecool.shop.dao;

import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

public interface ShoppingCartDao {

    void addItem(User user, Product product, int count);
    void removeItem(User user, Product product);
    void cartProcessed(User user);
    ShoppingCart getUserCart(User user);
}
