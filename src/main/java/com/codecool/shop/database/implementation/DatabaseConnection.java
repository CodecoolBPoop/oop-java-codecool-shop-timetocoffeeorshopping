package com.codecool.shop.database.implementation;

import com.codecool.shop.database.DatabaseHandler;

import java.sql.*;

public class DatabaseConnection implements DatabaseHandler {

    // JDBC driver name and database URL
    static final String SERVER_URL = "polly.agency";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://" + SERVER_URL + "/codecoolshop";

    //  Database credentials
    static final String USER = "testuser";
    static final String PASS = "ASdf2954";

    static Connection conn = connect();

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

    public ResultSet getResultSetForQuery(String queryString) {
        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(queryString);
//            stmt.close();

            return rs;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }
}
