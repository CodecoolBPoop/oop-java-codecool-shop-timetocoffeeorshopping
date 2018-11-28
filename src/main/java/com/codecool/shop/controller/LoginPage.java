package com.codecool.shop.controller;

import com.codecool.shop.authentication.LogInAuthent;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("charset=UTF-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("page", "Login");
        engine.process("login.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        boolean isVerified = LogInAuthent.verifyPassword(name, password);
        context.setVariable("isVerified", isVerified);

        if (isVerified) {
            HttpSession session = request.getSession();
            session.setAttribute("name",name);
            engine.process("login.html", context, response.getWriter());

        } else {
            engine.process("login.html", context, response.getWriter());
        }
    }
}
