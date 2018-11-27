package com.codecool.shop.database.implementation;

import com.codecool.shop.database.DatabaseHandler;
import com.codecool.shop.model.User;

import java.sql.*;

public class DatabaseConnection implements DatabaseHandler {

    // JDBC driver name and database URL
    static final String SERVER_URL = "polly.agency";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://" + SERVER_URL + "/codecoolshop";

    //  Database credentials
    static final String USER = "testuser";
    static final String PASS = "ASdf2954";

    private static Connection conn = connect();

    private static final DatabaseConnection INSTANCE = new DatabaseConnection();

    private DatabaseConnection() {
    }


    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }


    private static Connection connect() {
        try { // try-with-resources
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection estabilished!");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
//        finally {
//            try {
//                if (conn != null)
//                    conn.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }//end finally try
//        }
        return conn;
    }

    public ResultSet executeQuery(String queryString) {
        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = queryString;
            ResultSet rs = stmt.executeQuery(sql);

            return rs;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public User getUserObject(String userName) {
        User user;
        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM auth_user WHERE name = 'test';";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int userId = rs.getInt("id");
            String userEmail = rs.getString("email");
            String userPassword = rs.getString("password");
            String userSession = rs.getString("session_id");

            System.out.println("user ID:      " + rs.getInt("id"));
            System.out.println("user NAME:    " + rs.getString("name"));
            System.out.println("user EMAIL:   " + rs.getString("email"));
            System.out.println("user PW:      " + rs.getString("password"));
            System.out.println("user Session: " + rs.getString("session_id"));
            user = new User(userId, userName, userEmail, userPassword, userSession);

            //Clean-up environment
            rs.close();
            stmt.close();
            return user;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }
}
