package com.codecool.shop.database;

import com.codecool.shop.model.User;

import java.sql.ResultSet;

public interface DatabaseHandler {
    ResultSet getResultSetForQuery(String queryString);
}
