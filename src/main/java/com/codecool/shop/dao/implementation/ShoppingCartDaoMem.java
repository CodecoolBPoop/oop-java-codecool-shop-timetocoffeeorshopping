package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoMem implements ShoppingCartDao {

    int id = 0;
    int nextId = 0;

    private List<ShoppingCart> shoppingCarts = new ArrayList<>();
    private static ShoppingCartDaoMem instance = null;

    @Override
    public void cartOrderPlaced(String user) {
        ShoppingCart userCart = getUserCart(user);
        userCart.isProcessed();
    }

    public static ShoppingCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMem();
        }
        return instance;
    }

    @Override
    public void addItem(String user, Product product, int count) {
        ShoppingCart userCart = getUserCart(user);
        userCart.addProduct(product, count);
    }

    @Override
    public void removeItem(String user, Product product) {
        ShoppingCart userCart = getUserCart(user);
        userCart.removeProduct(product);
    }

    @Override
    public ShoppingCart getUserCart(String user) {
        for (ShoppingCart sc : shoppingCarts) {
            if (sc.getUser().equals(user) && sc.getIsActive()) {
                return sc;
            }
        }
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);

        shoppingCarts.add(newCart);
        return newCart;
    }
}
