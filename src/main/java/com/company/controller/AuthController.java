package com.company.controller;

import com.company.configs.security.UserDetails;
import com.company.domains.TrolleyProduct;
import com.company.dto.UserCreateDTO;
import com.company.services.AuthService;
import com.company.services.TrolleyService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${product.cover.path}")
    private String path;
    private final TrolleyService trolleyService;
    private final AuthService service;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("dto", new UserCreateDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSave(@Valid @ModelAttribute("dto") UserCreateDTO dto
            , BindingResult result) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        service.generate(dto);
        return "auth/login";
    }




    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }


    @GetMapping(value = "/history")
    public String historyPage(@RequestParam Optional<String> search,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "limit") Optional<Integer> limit,
                              Model model
            , @AuthenticationPrincipal UserDetails userDetails) {
        String searchQuery = search.orElse("");
        Page<TrolleyProduct> products = trolleyService.userProductAll(searchQuery, page, limit, userDetails.getAuthUser().getId());

        model.addAttribute("search", searchQuery);
        model.addAttribute("page", products);
        model.addAttribute("products", products.getContent());
        model.addAttribute("pageNumbers", IntStream.range(0, products.getTotalPages()).toArray());

        return "auth/auth_history";
    }

}