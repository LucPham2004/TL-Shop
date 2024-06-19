package com.e_shop.Shoe_Shop.Entity.order.detail;

import com.e_shop.Shoe_Shop.Entity.order.Order;
import com.e_shop.Shoe_Shop.Entity.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "shipping_cost")
    private Float shippingCost;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "sub_total")
    private Float subtotal;

    @ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
	private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Integer quantity, Float shippingCost, Float unit_price, Float subtotal, Product product,
            Order order) {
        this.quantity = quantity;
        this.shippingCost = shippingCost;
        this.unitPrice = unit_price;
        this.subtotal = subtotal;
        this.product = product;
        this.order = order;
    }

    public Float calculateSubtotal() {
        return (this.unitPrice * this.quantity) + this.shippingCost;
    }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", quantity=" + quantity + ", shippingCost=" + shippingCost + ", unit_price="
                + unitPrice + ", subtotal=" + subtotal + ", product=" + product + ", order=" + order + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;   
        this.subtotal = calculateSubtotal();
    }

    public Float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Float shippingCost) {
        this.shippingCost = shippingCost;
        this.subtotal = calculateSubtotal();
    }

    public Float getUnit_price() {
        return unitPrice;
    }

    public void setUnit_price(Float unitPrice) {
        this.unitPrice = unitPrice;
        this.subtotal = calculateSubtotal();
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
