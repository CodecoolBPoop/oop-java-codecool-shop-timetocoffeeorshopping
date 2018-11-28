package com.codecool.shop.controller;

import com.codecool.shop.password.HashAndAuthenticate;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        boolean isVerified = false;

        try {
            isVerified = HashAndAuthenticate.verifyPassword(email, password);
            System.out.println("isVerified = " + isVerified);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        context.setVariable("isVerified", isVerified);
        context.setVariable("page", "Login");


        if (isVerified) {
            HttpSession session = request.getSession();
            String sessionID = session.getId();
//            adatbázisba kiírni
            engine.process("login.html", context, response.getWriter());

        } else {
            engine.process("login.html", context, response.getWriter());
        }
    }
}
