package com.e_shop.Shoe_Shop.Entity.order;

import java.sql.Time;
import java.util.*;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.order.detail.OrderDetail;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "order_date")
    private Date date;

    @Column(name = "order_time")
    private Time orderTime;

    @Column(name = "shipping_cost")
    private Float shippingCost;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "order_status")
    private String status;

    @Column(name = "order_total")
    private Float total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;
    
    public Order(Date date, Time orderTime, Float shippingCost, Float tax, String status, Float total,
            Customer customer) {
        this.date = date;
        this.orderTime = orderTime;
        this.shippingCost = shippingCost;
        this.tax = tax;
        this.status = status;
        this.total = total;
        this.customer = customer;
    }

    public Order() {
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    @Override
    public String toString() {
        return "Order [id=" + id + ", date=" + date + ", time=" + orderTime + ", shippingCost=" + shippingCost
                + ", tax=" + tax + ", status=" + status + ", total=" + total + ", customer=" + customer + "]";
    }

}
