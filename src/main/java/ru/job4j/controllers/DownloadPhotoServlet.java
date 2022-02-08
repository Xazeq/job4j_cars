package ru.job4j.controllers;

import ru.job4j.services.AdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DownloadPhotoServlet extends HttpServlet {
    AdService service = AdService.instOf();
    Properties cfg = service.getAppCfg();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("id");
        File file = new File(cfg.getProperty("image.storage") + name + cfg.getProperty("image.extension"));
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(file)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
