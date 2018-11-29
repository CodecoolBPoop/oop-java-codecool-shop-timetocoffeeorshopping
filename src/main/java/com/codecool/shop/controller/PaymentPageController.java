package com.codecool.shop.controller;

import com.codecool.shop.config.DataHandler;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
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

@WebServlet(urlPatterns = {"/payment"})
public class PaymentPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = ShoppingCartContentHandler.getUserName(req);
        User user = ShoppingCartContentHandler.getUser(req);
        System.out.println("user:::" +userName);



        resp.setContentType("charset=UTF-8");
        ShoppingCart userCart = ShoppingCartContentHandler.getShoppingCart(user);


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        // Getting number of items in the cart for the navbar
        context.setVariable("cartItems", userCart.getItemsNumber());

        context.setVariable("totalPrice", userCart.getTotalPrice());
        context.setVariable("page", "Payment");
        context.setVariable("user", userName);
        engine.process("payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = ShoppingCartContentHandler.getUserName(req);

        OrderDao orderDataStore = OrderDaoMem.getInstance();
        Order userOrder = orderDataStore.getUserOrder(user);
        DataHandler.dbQuery.removeAllProductsFromCart(DataHandler.dbQuery.getUserCart(DataHandler.dbQuery.getUserObjectBySession(req.getSession().getId())));
        userOrder.setIsPayed(true);
        resp.sendRedirect("/");
    }
}
