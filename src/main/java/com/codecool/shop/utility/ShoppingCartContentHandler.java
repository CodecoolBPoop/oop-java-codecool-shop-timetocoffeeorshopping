package com.codecool.shop.utility;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.database.DatabaseQuery;
import com.codecool.shop.database.implementation.ExecuteQuery;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ShoppingCartContentHandler {
    public static List<Product> getProducts(HashMap productHashMap) {
        List<Product> products = new ArrayList<>();

        for (Object o : productHashMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            products.add((Product) entry.getKey());
        }
        return products;
    }

    public static ShoppingCart getShoppingCart(User user) {
        return DataHandler.dbQuery.getUserCart(user);
//        OrderDao orderDataStore = OrderDaoMem.getInstance();
//
//        Order userOrder = orderDataStore.getUserOrder(user);
//        userOrder.setShoppingCart(user);
//        return userOrder.getShoppingCart();
    }

    public static User getUser(HttpServletRequest req) {
        ExecuteQuery executeQuery = ExecuteQuery.getInstance();
        String reqid=req.getSession().getId();
        System.out.println("reqID:::"+reqid);
        User user = executeQuery.getUserObjectBySession(reqid);
//        try {
//            user = ;
//            username = user.getName();
//        } catch (Exception e) {
//            username = req.getSession().getId();
//            System.out.println("no user");
//        }
        return user;
    }

    public static String getUserName(HttpServletRequest request) {


        try {
            return getUser(request).getName();

        } catch (Exception e) {
            return "Guest";

        }
    }


}
