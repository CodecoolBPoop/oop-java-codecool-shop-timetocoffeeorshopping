package com.codecool.shop.database.implementation;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.database.DatabaseQuery;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.DropMode.INSERT;

public class ExecuteQuery implements DatabaseQuery {

    private static final ExecuteQuery INSTANCE = new ExecuteQuery();

    private ExecuteQuery() {
    }


    public static ExecuteQuery getInstance() {
        return INSTANCE;
    }

    public User getUserObjectByName(String userName) {
        User user;
        try {

            String sql = "SELECT * FROM auth_user WHERE name = 'test';";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            user = getUserObjectFromResultSet(rs);

            //Clean-up environment
            closeResultset(rs);
            return user;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public User getUserObjectBySession(String sessionId) {
        User user;
        try {
            String sql = "SELECT * FROM auth_user WHERE session_id = '" + sessionId +"';";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            user = getUserObjectFromResultSet(rs);

            //Clean-up environment
            closeResultset(rs);
            return user;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public User getUserObjectByEmail(String email) {
        User user;
        try {
            String query = "SELECT * FROM auth_user WHERE email = ?";

            try (PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet rs = statement.executeQuery();
                user = getUserObjectFromResultSet(rs);

                //Clean-up environment
                closeResultset(rs);
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public void registerNewUser(Customer customer) {

        try {
            String query =
                    "INSERT INTO auth_user(name, email, password) VALUES (?,?,?)";
            try (PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query)) {

                statement.setString(1, customer.getFirstName() + customer.getLastName());
                statement.setString(2, customer.getEmail());
                statement.setString(3, customer.getPassword());
                System.out.println(statement);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



           }

    private User getUserObjectFromResultSet (ResultSet rs) throws SQLException {
        rs.next();

        int userId = rs.getInt("id");
        String userName = rs.getString("name");
        String userEmail = rs.getString("email");
        String userPassword = rs.getString("password");
        String userSession = rs.getString("session_id");

        User user = new User(userId, userName, userEmail, userPassword, userSession);
        printUserDetails(user);
        return user;
    }

    private void printUserDetails(User user){
        System.out.println("user ID:      " + user.getId());
        System.out.println("user NAME:    " + user.getName());
        System.out.println("user EMAIL:   " + user.getEmail());
        System.out.println("user PW:      " + user.getPassword());
        System.out.println("user Session: " + user.getSessionId());
    }

    public ProductCategory getCategory(int id) {
        ProductCategory category;
        try {
            String sql = "SELECT * FROM product_category WHERE id = " + id + ";";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            rs.next();
            category = new ProductCategory(id, rs.getString("name"), rs.getString("department"), rs.getString("description"));
            //Clean-up environment
            closeResultset(rs);
            return category;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    private void closeResultset(ResultSet rs) throws SQLException{
        rs.getStatement().close();
        rs.close();
    }
}
