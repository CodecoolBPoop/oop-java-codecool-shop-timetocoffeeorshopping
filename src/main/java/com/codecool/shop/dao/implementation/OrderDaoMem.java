package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    int id = 0;

    private List<Order> orders = new ArrayList<>();
    private static OrderDaoMem instance = null;


    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public Order getUserOrder(String user) {

        for (Order order : orders) {
            if (order.getUser().equals(user) && order.getIsActive()) {
                return order;
            }
        }

        Order newOrder = new Order();
        newOrder.setUser(user);
//        newOrder.setShoppingCart(user);
//        newOrder.setCustomer(User);

        orders.add(newOrder);
        return newOrder;
    }

    @Override
    public void orderProcessed(String user) {
        Order order = getUserOrder(user);
        order.isProcessed();
    }
}
