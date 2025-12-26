package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/products")
    public String products(Model model) {
        return "products/list";
    }

    @GetMapping("/products/list")
    public String productsList(Model model) {
        return "products/list";
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        return "transactions/list";
    }

    @GetMapping("/transactions/list")
    public String transactionsList(Model model) {
        return "transactions/list";
    }

    @GetMapping("/dashboard_standalone")
    public String dashboardStandalone(Model model) {
        return "dashboard_standalone";
    }
}