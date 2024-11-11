package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import java.util.List;

import org.springframework.data.domain.Page;

import com.e_shop.Shoe_Shop.DTO.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.DTO.dto.OrderDTO;
import com.e_shop.Shoe_Shop.DTO.dto.ProductInfoDTO;

public class MainEntitiesSummary {
    
    private List<CustomerDTO> new_customers;
    private Page<OrderDTO> orderList;
    private List<ProductInfoDTO> lowRemainingProducts;
    
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
