package com.e_shop.Shoe_Shop.DTO.dto.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import com.e_shop.Shoe_Shop.DTO.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.DTO.dto.OrderDTO;
import com.e_shop.Shoe_Shop.DTO.dto.ProductInfoDTO;

public class MainEntitiesSummary {
    
	private long totalProducts;
	private long totalCustomers;
	private long totalOrders;

    private Float totalRevenue;
    
    private List<CustomerDTO> new_customers;
    private Page<OrderDTO> orderList;
    private List<ProductInfoDTO> lowRemainingProducts;

    
    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }


    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public List<CustomerDTO> getNew_customers() {
        return new_customers;
    }
    public void setNew_customers(List<CustomerDTO> new_customers) {
        this.new_customers = new_customers;
    }
    public Page<OrderDTO> getOrderList() {
        return orderList;
    }
    public void setOrderList(Page<OrderDTO> orderList) {
        this.orderList = orderList;
    }
    public List<ProductInfoDTO> getLowRemainingProducts() {
        return lowRemainingProducts;
    }
    public void setLowRemainingProducts(List<ProductInfoDTO> lowRemainingProducts) {
        this.lowRemainingProducts = lowRemainingProducts;
    }
}
