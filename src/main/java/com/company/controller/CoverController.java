package com.company.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;



@Controller
public class CoverController {

    @RequestMapping("/showCover")

    public void showCover(@RequestParam("img") String img, HttpServletResponse resp) {
        ServletOutputStream outputStream;
        try {
            outputStream = resp.getOutputStream();
            Path path = Path.of(img);
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
