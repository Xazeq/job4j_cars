package ru.job4j.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.models.Brand;
import ru.job4j.services.AdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BrandFilterServlet extends HttpServlet {
    private final AdService service = AdService.instOf();
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; character=utf-8");
        int brandId = Integer.parseInt(req.getParameter("brandId"));
        Brand brand = service.findBrandById(brandId);
        OutputStream out = resp.getOutputStream();
        String json = GSON.toJson(service.findAdsByBrand(brand.getName()));
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
