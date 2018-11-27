package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.database.DatabaseHandler;
import com.codecool.shop.database.implementation.DatabaseConnection;
import com.codecool.shop.model.ShoppingCart;

public class DataHandler {

    public static DatabaseHandler dbHandler = DatabaseConnection.getInstance();
    public static ProductDao productDataStore = ProductDaoMem.getInstance();
    public static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    public static SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
    public static ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
    public static ShoppingCart userCart(String user){
        return shoppingCartDataStore.getUserCart(user);
    }

}
