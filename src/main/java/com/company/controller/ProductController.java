package com.company.controller;

import com.company.configs.security.UserDetails;
import com.company.domains.Product;
import com.company.domains.TrolleyProduct;
import com.company.dto.ProductCreateDTO;
import com.company.dto.TrolleyProductCreateDTO;
import com.company.enums.Currency;
import com.company.enums.Gender;
import com.company.enums.ProductSize;
import com.company.enums.ProductType;
import com.company.repository.ProductRepository;
import com.company.services.ProductService;
import com.company.services.TrolleyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService service;
    private final TrolleyService trolleyService;

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String shopPage(@RequestParam Optional<String> search,
            @RequestParam(name = "page") Optional<Integer> page,
                           @RequestParam(name = "limit") Optional<Integer> limit,
                           Model model) {
        String searchQuery = search.orElse("");
        Page<Product> products = service.productAll(searchQuery,page, limit);

        model.addAttribute("search", searchQuery);
        model.addAttribute("page", products);
        model.addAttribute("products", products.getContent());
        model.addAttribute("pageNumbers", IntStream.range(0, products.getTotalPages()).toArray());
//        model.addAttribute("products", productList);
        return "shop";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("currencys", Currency.values());
        model.addAttribute("productTypes", ProductType.values());
        return "admin/add";
    }

    @PostMapping("/add")
    String productSave(@ModelAttribute ProductCreateDTO dto) {
        service.save(dto);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String detailsPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.get(id));
        model.addAttribute("size", ProductSize.values());
        return "product";
    }


    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public String AddTrolley(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @ModelAttribute TrolleyProductCreateDTO dto) {

        TrolleyProduct trolleyProduct = trolleyService.findByProductId(id, userDetails.getAuthUser().getId(),ProductSize.findByName(dto.getSize()));
        Optional<Product> product = service.finById(id);
        if (Objects.nonNull(trolleyProduct)) {
            trolleyService.updateTrolleyProduct(trolleyProduct, dto);
        } else {
            trolleyService.save(dto, userDetails, product);
        }
        return "redirect:/shop";
    }


    @RequestMapping(value = "product/delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {

        model.addAttribute("product", service.get(id));
        return "admin/product_delete";
    }

    @RequestMapping(value = "product/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "product/update/{id}", method = RequestMethod.GET)
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("currency", Currency.values());
        model.addAttribute("productTypes", ProductType.values());
        model.addAttribute("product", service.get(id));
        return "admin/product_update";
    }

    @RequestMapping(value = "product/update/{id}",method =RequestMethod.POST )
    public String update(@PathVariable Long id,ProductCreateDTO dto,Model model){
        model.addAttribute("genders", Gender.values());
        model.addAttribute("currency", Currency.values());
        model.addAttribute("productTypes", ProductType.values());
        model.addAttribute("product", service.get(id));
        service.update(id,dto);
        System.out.println("update");
        return "redirect:/admin";
    }


}
