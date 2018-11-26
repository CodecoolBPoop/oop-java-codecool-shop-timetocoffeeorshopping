package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.sun.xml.internal.bind.v2.TODO;
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
public class CheckoutPage extends HttpServlet {
    ShoppingCart userCart;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO
        String user = "sanya";

        resp.setContentType("charset=UTF-8");
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        Order userOrder = orderDataStore.getUserOrder(user);
        userCart = userOrder.getShoppingCart();
        userOrder.setShoppingCart(user);

        HashMap productHashMap = userCart.getProducts();

        List<Product> products = new ArrayList<Product>();

        Iterator it = productHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            products.add((Product) entry.getKey());
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cartItems", userCart.getItemsNumber());
        context.setVariable("userCart", userCart);
        context.setVariable("products", products);
        context.setVariable("productHashMap", productHashMap);
        context.setVariable("page", "Checkout");

        engine.process("checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
