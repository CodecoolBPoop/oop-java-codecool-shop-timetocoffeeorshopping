package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart"})
public class ShoppingCartPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();

        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");

        HashMap productHashMap = userCart.getProducts();

        List<Product> products = new ArrayList<Product>();

        Iterator it = productHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            products.add((Product) entry.getKey());
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("userCart", userCart);
        context.setVariable("products", products);
        context.setVariable("productHashMap", productHashMap);

        // Getting number of items in the cart for the navbar
        context.setVariable("cartItems", userCart.getItemsNumber());

        // Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Your shopping cart");

        engine.process("cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();

        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");

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
