package com.codecool.shop.database;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.User;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface DatabaseQuery {
    User getUserObjectByName(String userName);
    User getUserObjectBySession(String sessionId);
    ProductCategory getCategory(int id);
    ArrayList<ProductCategory> getCategories();
}
