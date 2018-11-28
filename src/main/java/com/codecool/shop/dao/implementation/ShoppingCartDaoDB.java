package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoDB implements ShoppingCartDao {

    private static ShoppingCartDaoDB instance = null;

    @Override
    public void cartProcessed(User user) {
        ShoppingCart userCart = getUserCart(user);
        userCart.isProcessed();
    }

    public static ShoppingCartDaoDB getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoDB();
        }
        return instance;
    }

    @Override
    public void addItem(User user, Product product, int count) {
        ShoppingCart cart = DataHandler.dbQuery.getUserCart(user);
        DataHandler.dbQuery.addProductToCart(cart, product, count);
    }

    @Override
    public void removeItem(User user, Product product) {
        DataHandler.dbQuery.removeProductFromCart(user, product);
    }

    @Override
    public ShoppingCart getUserCart(User user) {
        return DataHandler.dbQuery.getUserCart(user);
    }
}
