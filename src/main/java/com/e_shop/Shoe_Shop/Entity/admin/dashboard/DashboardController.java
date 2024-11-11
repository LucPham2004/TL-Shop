package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Entity.admin.dto.CustomerSummary;
import com.e_shop.Shoe_Shop.Entity.admin.dto.MainEntitiesSummary;
import com.e_shop.Shoe_Shop.Entity.admin.dto.OrderSummary;
import com.e_shop.Shoe_Shop.Entity.admin.dto.ProductSummary;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/admin/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping("/main-entities")
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
