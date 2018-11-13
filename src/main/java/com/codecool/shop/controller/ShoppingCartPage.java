//package com.codecool.shop.controller;
//
//import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.ProductCategoryDao;
//import com.codecool.shop.dao.ProductDao;
//import com.codecool.shop.dao.ShoppingCartDao;
//import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
//import com.codecool.shop.dao.implementation.ProductDaoMem;
//import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
//import com.codecool.shop.model.Product;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.WebContext;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.util.List;
//
//@WebServlet(urlPatterns = {"/cart"})
//public class ShoppingCartPage extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
//
//        System.out.println("itemsInShoppingCart ASD");
//        int itemsCountInShoppingCart = ((ShoppingCartDaoMem) shoppingCartDataStore).getCountByUser("sanya");
//        System.out.println("itemsInShoppingCart " + itemsCountInShoppingCart);
//
//        List<CartItem> cartItemsByUser = shoppingCartDataStore.getByUser("sanya");
//
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//        WebContext context = new WebContext(req, resp, req.getServletContext());
//
//
//        int totalCost = 0;
//
//        for (CartItem item : cartItemsByUser){
//            System.out.println("item " + item);
//            System.out.println("item.getProduct() " + item.getProduct());
//            totalCost += (item.getProduct().getDefaultPrice() * item.getCount());
//        }
//
//        String totalCostMsg = "Total cost: " + String.valueOf(totalCost) + " ";
//        System.out.println("totalCostMsg:: " + totalCostMsg);
//
////        ResultSet rs = ConnectToDB.executeQuery("Select * from product");
//        context.setVariable("recipient", "World");
//        context.setVariable("category", productCategoryDataStore.find(1));
//        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
//        context.setVariable("cartItemsNum", itemsCountInShoppingCart);
//        context.setVariable("cartItems", cartItemsByUser);
//        context.setVariable("totalCostMsg", totalCostMsg);
//        engine.process("shoppingCartPage.html", context, resp.getWriter());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String user = req.getParameter("user");
//        Integer cartItemId = Integer.valueOf(req.getParameter("delete"));
//
//        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
//
//        shoppingCartDataStore.remove(cartItemId);
//
//        this.doGet(req, resp);
//    }
//}
