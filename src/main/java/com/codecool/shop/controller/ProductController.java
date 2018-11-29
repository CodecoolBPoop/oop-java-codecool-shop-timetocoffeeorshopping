package com.codecool.shop.controller;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartDaoDB;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        User user = DataHandler.dbQuery.getUserObjectByName("readdeo");
//        ShoppingCart usercart = DataHandler.dbQuery.getUserCart(user);
//
//        Product product = DataHandler.dbQuery.getProduct(1);
//        System.out.println("Adding product to cart " + usercart);
//        usercart.addProduct(product, 1);

//        DataHandler.dbQuery.getUserObjectByName("test");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

// Getting number of items in the cart for the navbar
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoDB.getInstance();

//        context.setVariable("cartItems", userCart.getItemsNumber());

// Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Store");

        context.setVariable("categories", DataHandler.productCategoryDataStore.getAll());
        context.setVariable("suppliers", DataHandler.supplierDataStore.getAll());
        ArrayList<Product> products = DataHandler.dbQuery.getAllProducts();
        System.out.println(products.get(0).getName());
        context.setVariable("products", products);

        if (req.getParameter("category") != null &&
                Integer.parseInt(req.getParameter("category")) <= DataHandler.productCategoryDataStore.getAll().size()) {
//            context.setVariable("products", DataHandler.productDataStore.getBy
//                    (DataHandler.productCategoryDataStore.find(Integer.valueOf(req.getParameter("category")))));
            context.setVariable("category", DataHandler.productCategoryDataStore.find
                    (Integer.parseInt(req.getParameter("category"))).getName());
        } else if (req.getParameter("supplier") != null &&
                Integer.parseInt(req.getParameter("supplier")) <= DataHandler.supplierDataStore.getAll().size()) {

            context.setVariable("supplier", DataHandler.dbQuery.getSupplier(1));
        } else {
//            context.setVariable("products", DataHandler.productDataStore.getAll());
        }

        engine.process("product/indexv2.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String param = e.nextElement().toString();
        }

        String command = req.getParameter("command");
        if (command.equals("add")) {
            int productId = Integer.valueOf(req.getParameter("product"));

            Product prod = DataHandler.productDataStore.find(productId);

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
//            context.setVariable("items", DataHandler.userCart("sanya").getItemsNumber());
            engine.process("updateItemsNum.html", context, resp.getWriter());
        }
    }
}
