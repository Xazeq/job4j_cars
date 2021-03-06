package ru.job4j.controllers;

import ru.job4j.models.User;
import ru.job4j.services.AdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginServlet extends HttpServlet {
    private final AdService service = AdService.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = service.findUserByUsername(username);
        OutputStream out = resp.getOutputStream();
        if (user != null && password.equals(user.getPassword())) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            out.write("200".getBytes(StandardCharsets.UTF_8));
        } else {
            out.write("400".getBytes(StandardCharsets.UTF_8));
        }
        out.flush();
        out.close();
    }
}
