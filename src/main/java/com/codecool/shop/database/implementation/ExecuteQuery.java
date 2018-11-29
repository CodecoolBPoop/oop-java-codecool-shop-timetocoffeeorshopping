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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.swing.DropMode.INSERT;

import com.codecool.shop.model.*;

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

            String sql = "SELECT * FROM auth_user WHERE name LIKE 'readdeo';";
            System.out.println("getuserbyname: " + sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            user = getUserObjectFromResultSet(rs);
            System.out.println("getuserbyname: " + user);

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
            String sql = "SELECT * FROM auth_user WHERE session_id = '" + sessionId + "';";
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

    private void printUserDetails(User user) {
        System.out.println("user ID:      " + user.getId());
        System.out.println("user NAME:    " + user.getName());
        System.out.println("user EMAIL:   " + user.getEmail());
        System.out.println("user PW:      " + user.getPassword());
        System.out.println("user Session: " + user.getSessionId());
    }

    public ProductCategory getCategory(int id) {
        ProductCategory category;
        try {
            String sql = "SELECT * FROM product_category WHERE id = " + id + " LIMIT 1;";
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

    public Supplier getSupplier(int id) {
        Supplier supplier;
        try {
            String sql = "SELECT * FROM supplier WHERE id = " + id + " LIMIT 1;";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            rs.next();
            supplier = new Supplier(id, rs.getString("name"), rs.getString("description"));
            //Clean-up environment
            closeResultset(rs);
            return supplier;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public Product getProduct(int id) {
        Product product;
        try {
            String sql = "SELECT * FROM product WHERE id = " + id + " LIMIT 1;";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            rs.next();

            ProductCategory category = getCategory(rs.getInt("product_category"));
            Supplier supplier = getSupplier(rs.getInt("supplier"));

            product = new Product(id,
                    rs.getString("name"),
                    rs.getFloat("defaultprice"),
                    rs.getString("default_currency"),
                    rs.getString("description"),
                    category,
                    supplier);

            //Clean-up environment
            closeResultset(rs);
            return product;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public HashMap<Product, Integer> getProductsInCart(int id) {
        HashMap<Product, Integer> products = new HashMap<Product, Integer>();
        try {
            String sql = "SELECT * FROM cart_items WHERE shoppingcart = " + id + ";";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            while (rs.next()) {
                Product product = getProduct(rs.getInt("product"));
                products.put(product, rs.getInt("number_of_product"));
            }

            //Clean-up environment
            closeResultset(rs);
            return products;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public ArrayList<ProductCategory> getCategories() {
        ProductCategory category;
        ArrayList<ProductCategory> categoryList = new ArrayList<ProductCategory>();

        try {
            String sql = "SELECT * FROM product_category;";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            while (rs.next()) {
                category = new ProductCategory(rs.getInt("id"), rs.getString("name"), rs.getString("department"), rs.getString("description"));
                categoryList.add(category);
            }

            //Clean-up environment
            closeResultset(rs);
            return categoryList;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public ShoppingCart getUserCart(User user) {
        ShoppingCart userCart;
        try {
            String sql = "SELECT * FROM shopping_cart WHERE user_id = " + user.getId() + " LIMIT 1;";
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            rs.next();

            int shoppingCartId = rs.getInt("id");

            HashMap<Product, Integer> products = getProductsInCart(shoppingCartId);

            userCart = new ShoppingCart(shoppingCartId, user, null, products, rs.getBoolean("active"));
            System.out.println("CART ID " + shoppingCartId + " " + rs.getInt("user_id"));
            //Clean-up environment
            closeResultset(rs);
            return userCart;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    @Override
    public void addProductToCart(ShoppingCart cart, Product product, int count) {

        System.out.println("CART ID ADD " + cart.getId());

        try {
            String sql = "INSERT INTO cart_items VALUES (" + cart.getId() + ", " + product.getId() + ", " + count + ");";
//            sql= "INSERT INTO cart_items VALUES (1,1,1);";
            System.out.println(sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            //Clean-up environment
            closeResultset(rs);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public void removeProductFromCart(User user, Product product) {

    }

//    public Product getProduct(int id) {
//        Product product;
//        try {
//            String sql = "SELECT * FROM product WHERE id = " + id + " LIMIT 1;";
//            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
//            rs.next();
//
//            String name = rs.getString("name");
//            Float defPrice = rs.getFloat("defaultprice");
//            String currency = rs.getString("default_currency");
//            String description = rs.getString("description");
//            ProductCategory category = getCategory(rs.getInt("product_category"));
//            Supplier supplier = getSupplier(rs.getInt("supplier"));
//
//            product = new Product( id, name, defPrice, currency, description, category, supplier);
//            //Clean-up environment
//            closeResultset(rs);
//            return product;
//        } catch (Exception e) {
//            System.out.println("Exception: " + e);
//        }
//        return null;
//    }

    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            String sql = "SELECT * FROM product;";
            System.out.println(sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);

            while (rs.next()) {
                System.out.println("product ITER " + rs.getInt("id"));

                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float defPrice = rs.getFloat("defaultprice");
                String currency = rs.getString("default_currency");
                String description = rs.getString("description");
                ProductCategory category = getCategory(rs.getInt("product_category"));
                Supplier supplier = getSupplier(rs.getInt("supplier"));

                System.out.println(name);

                Product product = new Product(id, name, defPrice, currency, description, category, supplier);
                System.out.println(product.getName());
                products.add(product);
            }
            System.out.println("EXEC products: " + products);
            System.out.println("EXEC products: " + products.size());

            //Clean-up environment
            closeResultset(rs);
            return products;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    @Override
    public void editProductQuantityInCart(ShoppingCart cart, Product product, int count) {

        System.out.println("CART ID ADD " + cart.getId());

        try {
            String sql = "UPDATE cart_items SET number_of_product = "+count+" WHERE shoppingcart = "+cart.getId()+" AND product = "+product.getId()+";";
//            sql= "INSERT INTO cart_items VALUES (1,1,1);";
            System.out.println(sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            //Clean-up environment
            closeResultset(rs);
        } catch (Exception e) {
            System.out.println("Exception editProductQuantityInCart: " + e);
        }
    }

    @Override
    public void removeProductFromCart(ShoppingCart cart, Product product) {

        System.out.println("CART ID ADD " + cart.getId());

        try {
            String sql = "DELETE FROM cart_items WHERE shoppingcart = "+cart.getId()+" AND product = "+product.getId()+";";
            System.out.println(sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            //Clean-up environment
            closeResultset(rs);
        } catch (Exception e) {
            System.out.println("Exception editProductQuantityInCart: " + e);
        }
    }

    private void closeResultset(ResultSet rs) {
        try {
            rs.getStatement().close();
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void writeSessionIdToDatabase(String email, String sessionId) {
        try {
            String query = "UPDATE auth_user SET session_id=? WHERE email= ? ";

            PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query);

                statement.setString(2, email);
                statement.setString(1, sessionId);
                System.out.println(statement);
                statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeAllProductsFromCart(ShoppingCart cart) {

        System.out.println("CART ID ADD " + cart.getId());

        try {
            String sql = "DELETE FROM cart_items WHERE shoppingcart = "+cart.getId()+";";
            System.out.println(sql);
            ResultSet rs = DataHandler.dbHandler.getResultSetForQuery(sql);
            //Clean-up environment
            closeResultset(rs);
        } catch (Exception e) {
            System.out.println("Exception editProductQuantityInCart: " + e);
        }
    }
}
