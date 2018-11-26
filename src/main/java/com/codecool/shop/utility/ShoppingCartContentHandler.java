package com.codecool.shop.utility;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

import java.util.*;

public class ShoppingCartContentHandler {
    public static List<Product> getProducts(HashMap productHashMap) {
        List<Product> products = new ArrayList<Product>();

        Iterator it = productHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            products.add((Product) entry.getKey());
        }
        return products;
    }

    public static ShoppingCart getShoppingCart(String user) {
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        Order userOrder = orderDataStore.getUserOrder(user);
        userOrder.setShoppingCart(user);
        return userOrder.getShoppingCart();
    }

}
