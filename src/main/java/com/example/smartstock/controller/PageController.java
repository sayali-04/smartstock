package com.example.smartstock.controller;

import com.example.smartstock.model.Product;
import com.example.smartstock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home() {
        return "redirect:/products-page";
    }

    @GetMapping("/products-page")
    public String showProductsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/stock-page")
    public String showStockPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "stock";
    }

    @GetMapping("/alerts-page")
    public String showAlertsPage() {
        return "alerts";
    }
}