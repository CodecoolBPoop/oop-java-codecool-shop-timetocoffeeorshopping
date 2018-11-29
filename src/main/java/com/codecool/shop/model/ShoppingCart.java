package com.codecool.shop.model;

import com.codecool.shop.config.DataHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShoppingCart {
    public int getId() {
        return id;
    }

    private int id;
    private User user;
    private LocalDateTime orderPlacedDate;
    private HashMap products;
    private Boolean isActive;

    public ShoppingCart (int id, User user, LocalDateTime orderPlacedDate, HashMap products, Boolean isActive){
        this.id = id;
        this.user = user;
        this.orderPlacedDate = orderPlacedDate;
        this.products = products;
        this.isActive = isActive;
    }

    public LocalDateTime getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public User getUser() {
        return user;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void isProcessed() {
        DataHandler.dbQuery.removeAllProductsFromCart(this);
        this.isActive = false;
        orderPlacedDate = LocalDateTime.now();
    }

    public HashMap getProducts() {
        return products;
    }

    public void addProduct(Product product, int num) {
        DataHandler.dbQuery.addProductToCart(this, product, num);
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
                Map.Entry entry = (Map.Entry) it.next();
                Product prod = ((Product) entry.getKey());
                totalPrice += prod.getPriceFloat() * (int) entry.getValue();
            }
        }
        return totalPrice;
    }

    public int getItemsNumber() {
        int itmemsNum = 0;
        if (!products.isEmpty()) {
            Iterator it = products.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Product prod = ((Product) entry.getKey());
                itmemsNum += (int)entry.getValue();
            }
        } else {
            return 0;
        }
        return itmemsNum;
    }

    public void editProductQuantity(Product product, int quantity){

    }

//    public void editNumOfProducts(Product product) {
//    }
}
