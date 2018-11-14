package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Supplier;

import java.util.Arrays;

public class OrderDaoMem {
    @Override
    public Order find(int id) {
        Arrays data;
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

}
