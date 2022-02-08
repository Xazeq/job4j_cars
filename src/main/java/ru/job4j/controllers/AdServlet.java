package ru.job4j.controllers;

import ru.job4j.models.User;
import ru.job4j.services.AdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdServlet extends HttpServlet {
    private final AdService adService = AdService.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        String color = req.getParameter("color");
        int mileage = Integer.parseInt(req.getParameter("mileage"));
        String desc = req.getParameter("desc");
        int price = Integer.parseInt(req.getParameter("price"));
        int brandId = Integer.parseInt(req.getParameter("brand"));
        int bodyId = Integer.parseInt(req.getParameter("body"));
        User user = (User) req.getSession().getAttribute("user");
        adService.addAd(model, year, color, mileage, desc, price, brandId, bodyId, user);
        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}
