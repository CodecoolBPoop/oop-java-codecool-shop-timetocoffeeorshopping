package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.database.implementation.ExecuteQuery;
import com.codecool.shop.model.Customer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/registration"})
public class RegistrationPageController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        resp.setContentType("charset=UTF-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("page", "Registration");

        engine.process("registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Customer customer = new Customer();
        customer.setFirstName(req.getParameter("firstname"));
        customer.setLastName(req.getParameter("lastname"));
        customer.setEmail(req.getParameter("email"));
        customer.setPassword(req.getParameter("password"));
        ExecuteQuery executeQuery = new ExecuteQuery();
        executeQuery.registerNewUser(customer);

        resp.sendRedirect("/");
    }
}


