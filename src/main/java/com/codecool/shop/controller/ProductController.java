package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

// Getting number of items in the cart for the navbar
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");
        context.setVariable("cartItems", userCart.getItemsNumber());

// Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Store");

        context.setVariable("categories", productCategoryDataStore.getAll());

        if (req.getParameter("category") != null) {
            context.setVariable("products", productDataStore.getBy
                    (productCategoryDataStore.find(Integer.valueOf(req.getParameter("category")))));
            System.out.println(productCategoryDataStore.find(Integer.valueOf(req.getParameter("category"))));
            context.setVariable("category", productCategoryDataStore.find
                    (Integer.parseInt(req.getParameter("category"))).getName());
        } else {
            context.setVariable("products", productDataStore.getAll());
        }

        engine.process("product/indexv2.html", context, resp.getWriter());
    }

}
