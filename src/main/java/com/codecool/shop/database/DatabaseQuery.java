package com.codecool.shop.database;

import com.codecool.shop.model.User;

import java.sql.ResultSet;

public interface DatabaseQuery {
    User getUserObjectByName(String userName);
    User getUserObjectBySession(String sessionId);
}
