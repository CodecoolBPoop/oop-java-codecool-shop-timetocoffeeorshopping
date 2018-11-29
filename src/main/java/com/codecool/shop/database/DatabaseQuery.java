package com.codecool.shop.database;

import com.codecool.shop.model.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public interface DatabaseQuery {
    User getUserObjectByName(String userName);
    User getUserObjectBySession(String sessionId);
    ProductCategory getCategory(int id);
    ArrayList<ProductCategory> getCategories();
    ShoppingCart getUserCart(User user);
    HashMap<Product, Integer> getProductsInCart(int id);
    Product getProduct(int id);
    Supplier getSupplier(int id);

    void addProductToCart(ShoppingCart cart, Product product, int count);

    void removeProductFromCart(User user, Product product);
    ArrayList<Product> getAllProducts();
    void editProductQuantityInCart(ShoppingCart cart, Product product, int count);
    void removeProductFromCart(ShoppingCart cart, Product product);
    void removeAllProductsFromCart(ShoppingCart cart);
}
