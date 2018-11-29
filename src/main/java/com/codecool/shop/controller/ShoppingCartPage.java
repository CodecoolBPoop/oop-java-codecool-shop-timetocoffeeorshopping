package com.codecool.shop.controller;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart"})
public class ShoppingCartPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        User user = DataHandler.dbQuery.getUserObjectByName("readdeo");
        ShoppingCart userCart = DataHandler.dbQuery.getUserCart(user);

        System.out.println("usercart iD: " + userCart.getId());

        HashMap productHashMap = DataHandler.dbQuery.getProductsInCart(userCart.getId());
        System.out.println("PROD HASH SIZE "+productHashMap.size());

        Product[] products = new Product[productHashMap.size()];

        Iterator it = productHashMap.entrySet().iterator();
        int counter = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Product product = (Product) pair.getKey();
            products[counter] = product;
            counter++;
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("userCart", userCart);
        context.setVariable("productHashMap", productHashMap);
        context.setVariable("products", products);

        // Getting number of items in the cart for the navbar
//        context.setVariable("cartItems", userCart.getItemsNumber());

        // Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Your shopping cart");

        engine.process("cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = DataHandler.dbQuery.getUserObjectByName("readdeo");
        ShoppingCart userCart = DataHandler.dbQuery.getUserCart(user);

        String command = req.getParameter("command");
        if (command.equals("editQuantity")) {
            System.out.println("newQuantity:" + req.getParameter("newQuantity"));
            int productId = Integer.valueOf(req.getParameter("product").substring(6));
            int newQuantity = Integer.valueOf(req.getParameter("newQuantity"));

            Product prod = DataHandler.dbQuery.getProduct(productId);

            System.out.println("prod:"+prod + " usercart "+ userCart);

            DataHandler.dbQuery.editProductQuantityInCart(userCart, prod, newQuantity);

        } else if (command.equals("removeProduct")) {
            System.out.println("REMOVE:" + req.getParameter("product"));
            int productId = Integer.valueOf(req.getParameter("product"));

            Product prod = DataHandler.dbQuery.getProduct(productId);
            DataHandler.dbQuery.removeProductFromCart(userCart, prod);
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariable("items", userCart.getItemsNumber());
        engine.process("updateItemsNum.html", context, resp.getWriter());
    }
}
