package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.*;
import com.codecool.shop.utility.ShoppingCartContentHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutPageController extends HttpServlet {
    private ShoppingCart userCart;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO
        resp.setContentType("charset=UTF-8");

        User user = ShoppingCartContentHandler.getUser(req);
        if (user == null) {
          resp.sendRedirect("/login");
        }
        userCart = ShoppingCartContentHandler.getShoppingCart(user);
        HashMap productHashMap = userCart.getProducts();

        List<Product> products = ShoppingCartContentHandler.getProducts(productHashMap);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("user", user.getName());
        context.setVariable("cartItems", userCart.getItemsNumber());
        context.setVariable("userCart", userCart);
        context.setVariable("products", products);
        context.setVariable("productHashMap", productHashMap);
        context.setVariable("page", "Checkout");

        engine.process("checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Customer customer = new Customer();
        customer.setFirstName(req.getParameter("firstname"));
        customer.setLastName(req.getParameter("lastname"));
        customer.setEmail(req.getParameter("email"));
        customer.setPassword(req.getParameter("password"));
        customer.setAdressShipping(req.getParameter("address"));
        customer.setCity(req.getParameter("city"));
        customer.setZip(req.getParameter("zip"));
        System.out.println("customer = " + customer.getEmail());

        Order order = new Order(customer, userCart);


        resp.sendRedirect("/payment");
    }
}
