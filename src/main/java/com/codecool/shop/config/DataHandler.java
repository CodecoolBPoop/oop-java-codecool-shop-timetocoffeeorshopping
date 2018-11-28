package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.database.DatabaseHandler;
import com.codecool.shop.database.DatabaseQuery;
import com.codecool.shop.database.implementation.DatabaseConnection;
import com.codecool.shop.database.implementation.ExecuteQuery;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;

public class DataHandler {

    public static DatabaseHandler dbHandler = DatabaseConnection.getInstance();
    public static ProductDao productDataStore = ProductDaoMem.getInstance();
    public static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
    public static SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
    public static ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoDB.getInstance();
    public static ShoppingCart userCart(User user){
        return shoppingCartDataStore.getUserCart(user);
    }
    public static DatabaseQuery dbQuery = ExecuteQuery.getInstance();

}
