package com.codecool.shop.database;

import com.codecool.shop.model.User;

public interface DatabaseHandler {
    User getUserObject(String userName);
}
