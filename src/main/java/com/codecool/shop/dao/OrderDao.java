package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

public interface OrderDao {
    void cartProcessed(String user);

    Order getUserOrder(String user);
}