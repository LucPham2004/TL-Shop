package com.e_shop.Shoe_Shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.dto.response.admin.CustomerSummary;
import com.e_shop.Shoe_Shop.dto.response.admin.MainEntitiesSummary;
import com.e_shop.Shoe_Shop.dto.response.admin.OrderSummary;
import com.e_shop.Shoe_Shop.dto.response.admin.ProductSummary;
import com.e_shop.Shoe_Shop.service.DashboardService;

@RestController
@RequestMapping("/api/v1/admin")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping("/summary")
    public MainEntitiesSummary getEntitiesSummary() {
        return dashboardService.entitiesSummary();
    }

    @GetMapping("/products")
    public ProductSummary getProductSummary() {
        return dashboardService.loadProductSummary();
    }

    @GetMapping("/customers")
    public CustomerSummary getCustomerSummary() {
        return dashboardService.loadCustomerSummary();
    }

    @GetMapping("/orders")
    public OrderSummary getOrderSummary() {
        return dashboardService.loadOrderSummary();
    }

}
