package com.codecool.shop.database.implementation;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.database.DatabaseQuery;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.DropMode.INSERT;

public class ExecuteQuery implements DatabaseQuery {

    private static final ExecuteQuery INSTANCE = new ExecuteQuery();

    public ExecuteQuery() {
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

    public void registerNewUser(Customer customer) {

        try {
            String query =
                    "INSERT INTO auth_user(name, email, password) VALUES (?,?,?)";
            PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query);

            statement.setString(1, customer.getFirstName() + customer.getLastName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPassword());
            System.out.println(statement);
            statement.executeUpdate();
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
        System.out.println("user Session: " + user.getSession());
    }

    private void closeResultset(ResultSet rs) throws SQLException{
        rs.getStatement().close();
        rs.close();
    }
}
