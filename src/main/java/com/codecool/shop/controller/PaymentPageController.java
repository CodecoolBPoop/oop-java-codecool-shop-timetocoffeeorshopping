package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.utility.ShoppingCartContentHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = "sanya";

        resp.setContentType("charset=UTF-8");
        ShoppingCart userCart = ShoppingCartContentHandler.getShoppingCart(user);


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        // Getting number of items in the cart for the navbar
        context.setVariable("cartItems", userCart.getItemsNumber());

        context.setVariable("totalPrice", userCart.getTotalPrice());
        context.setVariable("page", "Payment");
        engine.process("payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        this.doGet(req, resp);
        String user = "sanya";
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        Order userOrder = orderDataStore.getUserOrder(user);
        userOrder.isProcessed();
        userOrder.setIsPayed(true);
        resp.sendRedirect("/");
    }
}
