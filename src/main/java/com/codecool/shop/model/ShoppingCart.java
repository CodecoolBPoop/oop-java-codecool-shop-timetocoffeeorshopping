package com.codecool.shop.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShoppingCart {
    private String user;
    private LocalDateTime orderPlacedDate;
    private HashMap products = new HashMap();
    private Boolean isActive = true;

    public LocalDateTime getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void isProcessed() {
        this.isActive = false;
        orderPlacedDate = LocalDateTime.now();
    }

    public HashMap getProducts() {
        return products;
    }

    public void addProduct(Product product, int num) {
        if (!products.isEmpty() && products.containsKey(product)) {
            int numOfProducts = (int) products.get(product);
            products.put(product, num + numOfProducts);
        } else {
            products.put(product, num);
        }
    }

    public void removeProduct(Product product) {
        if (!products.isEmpty() && products.containsKey(product)) {
            products.remove(product);
        }
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        if (!products.isEmpty()) {
            Iterator it = products.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                Product prod = ((Product) entry.getKey());
                totalPrice += prod.getPriceFloat() * (int)entry.getValue();
            }
        }
        return totalPrice;
    }

//    public void editNumOfProducts(Product product) {
//    }
}
