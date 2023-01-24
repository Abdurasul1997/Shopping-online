package com.company.controller;


import com.company.domains.Product;
import com.company.enums.ProductSize;
import com.company.enums.ProductType;
import com.company.repository.ProductRepository;
import com.company.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductRepository productRepository;
    private final ProductService service;


    @RequestMapping
    @PreAuthorize("permitAll()")
    public String homPage(Model model) {

        List<Product> shoes = service.shoesAll(ProductType.findByName("SHOES"));
        List<Product> shirts = service.shoesAll(ProductType.findByName("SHIRT"));


        model.addAttribute("shoes", shoes);
        model.addAttribute("shirts", shirts);
        model.addAttribute("size", ProductSize.values());
        return "home";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String admin(@RequestParam Optional<String> search,
                        @RequestParam(name = "page") Optional<Integer> page,
                        @RequestParam(name = "limit") Optional<Integer> limit,
                        Model model) {
        String searchQuery = search.orElse("");
        Page<Product> product = service.productAll(searchQuery, page, limit);

        model.addAttribute("page", product);
        model.addAttribute("products", product.getContent());
        model.addAttribute("search", searchQuery);
        model.addAttribute("pageNumbers", IntStream.range(0, product.getTotalPages()).toArray());
//        model.addAttribute("products", productList);
        return "admin/admin";
    }


    @GetMapping("/home")
    public String homePage(Model model) {

        List<Product> shoes = service.shoesAll(ProductType.findByName("SHOES"));
        List<Product> shirts = service.shoesAll(ProductType.findByName("SHIRT"));


        model.addAttribute("shoes", shoes);
        model.addAttribute("shirts", shirts);
        model.addAttribute("size", ProductSize.values());
        return "home";
    }




}
