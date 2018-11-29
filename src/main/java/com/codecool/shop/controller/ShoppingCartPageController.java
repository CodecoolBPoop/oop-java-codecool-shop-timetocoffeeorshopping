package com.codecool.shop.controller;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductDaoDB;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.User;
import com.codecool.shop.utility.ShoppingCartContentHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart"})
public class ShoppingCartPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoDB.getInstance();

        User user = ShoppingCartContentHandler.getUser(req);
        if (user == null) {
            resp.sendRedirect("/login");
        }


        ShoppingCart userCart = DataHandler.dbQuery.getUserCart(user);
//        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");

        HashMap productHashMap = userCart.getProducts();

        List<Product> products = ShoppingCartContentHandler.getProducts(productHashMap);


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("userCart", userCart);
        context.setVariable("products", products);
        context.setVariable("productHashMap", productHashMap);

        // Getting number of items in the cart for the navbar
        context.setVariable("cartItems", userCart.getItemsNumber());

        // Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Your shopping cart");
        context.setVariable("user", user.getName());

        engine.process("cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDataStore = ProductDaoDB.getInstance();
//        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoDB.getInstance();
        User user = ShoppingCartContentHandler.getUser(req);

        ShoppingCart userCart = DataHandler.dbQuery.getUserCart(user);

        String command = req.getParameter("command");
        if (command.equals("editQuantity")) {
            System.out.println("newQuantity:" + req.getParameter("newQuantity"));
            int productId = Integer.valueOf(req.getParameter("product").substring(6));
            int newQuantity = Integer.valueOf(req.getParameter("newQuantity"));

            Product prod = productDataStore.find(productId);

            userCart.editProductQuantity(prod, newQuantity);

        } else if (command.equals("removeProduct")) {
            System.out.println("REMOVE:" + req.getParameter("product"));
            int productId = Integer.valueOf(req.getParameter("product"));

            Product prod = productDataStore.find(productId);

            userCart.removeProduct(prod);
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("items", userCart.getItemsNumber());
        engine.process("updateItemsNum.html", context, resp.getWriter());
    }
}
