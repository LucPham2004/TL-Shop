package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Date;
import java.util.Set;

import com.e_shop.Shoe_Shop.entity.OrderDetail;


public class OrderDTO {
    private Integer id;
    private Date date;
    private Float shippingCost;
    private Float tax;
    private String status;
    private Float total;
    private String customerName;
    private Set<OrderDetail> orderDetails;
    
    public OrderDTO() {
    }

    public OrderDTO(Integer id, Date date, Float shippingCost, Float tax, String status, Float total,
            String customerName, Set<OrderDetail> orderDetails) {
        this.id = id;
        this.date = date;
        this.shippingCost = shippingCost;
        this.tax = tax;
        this.status = status;
        this.total = total;
        this.customerName = customerName;
        this.orderDetails = orderDetails;
    }
    
    @Override
    public String toString() {
        return "OrderDTO [id=" + id + ", date=" + date + ", shippingCost=" + shippingCost + ", tax=" + tax + ", status="
                + status + ", total=" + total + ", customerName=" + customerName + ", orderDetails=" + orderDetails
                + "]";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Float getShippingCost() {
        return shippingCost;
    }
    public void setShippingCost(Float shippingCost) {
        this.shippingCost = shippingCost;
    }
    public Float getTax() {
        return tax;
    }
    public void setTax(Float tax) {
        this.tax = tax;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
