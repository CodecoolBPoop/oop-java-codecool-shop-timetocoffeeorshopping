package com.codecool.shop.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class ShoppingCart {
    private String user;
    private LocalDateTime orderPlacedDate;
    private HashMap products;
    private Boolean isActive;

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

//    public void editNumOfProducts(Product product) {
//    }
}
