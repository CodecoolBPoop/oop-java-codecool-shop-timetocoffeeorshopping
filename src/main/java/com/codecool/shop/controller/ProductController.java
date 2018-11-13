package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

// Getting number of items in the cart for the navbar
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");
        context.setVariable("cartItems", userCart.getItemsNumber());

// Telling the sidebar, which menu should be highlighted
        context.setVariable("page", "Store");

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        if (req.getParameter("category") != null &&
                Integer.parseInt(req.getParameter("category")) <= productCategoryDataStore.getAll().size()) {
            context.setVariable("products", productDataStore.getBy
                    (productCategoryDataStore.find(Integer.valueOf(req.getParameter("category")))));
            context.setVariable("category", productCategoryDataStore.find
                    (Integer.parseInt(req.getParameter("category"))).getName());
        } else if (req.getParameter("supplier") != null &&
                Integer.parseInt(req.getParameter("supplier")) <= supplierDataStore.getAll().size()) {
            context.setVariable("products", productDataStore.getBy
                    (supplierDataStore.find(Integer.valueOf(req.getParameter("supplier")))));
            context.setVariable("supplier", supplierDataStore.find
                    (Integer.parseInt(req.getParameter("supplier"))).getName());
        } else {
            context.setVariable("products", productDataStore.getAll());
        }

        engine.process("product/indexv2.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
        Enumeration e = req.getParameterNames();
        ShoppingCart userCart = shoppingCartDataStore.getUserCart("sanya");
        while (e.hasMoreElements()){
            String param = e.nextElement().toString();
            System.out.println(param);
        }

        System.out.println(e.toString());

        String command = req.getParameter("command");
        if (command.equals("add")) {
            int productId = Integer.valueOf(req.getParameter("product"));

            Product prod = productDataStore.find(productId);


            userCart.addProduct(prod, 1);
            System.out.println("AddToCart");

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());

            context.setVariable("items", userCart.getItemsNumber());

            engine.process("updateItemsNum.html", context, resp.getWriter());
        }

    }
}

