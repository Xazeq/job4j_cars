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

public class RegServlet extends HttpServlet {
    private final AdService service = AdService.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = service.findUserByUsername(username);
        if (user == null) {
            user = service.findUserByEmail(email);
        }
        if (user != null) {
            OutputStream out = resp.getOutputStream();
            out.write("400".getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
        } else {
            user = User.of(username, password, email);
            service.addUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("index.html");
        }
    }
}
