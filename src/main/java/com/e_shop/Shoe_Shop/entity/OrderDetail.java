package com.e_shop.Shoe_Shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_imageURL")
    private String productImage;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "size")
    private Integer size;

    @Column(name = "color")
    private String color;

    @Column(name = "categories")
    private String categories;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "sub_total")
    private Float subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
    @JsonBackReference
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
	private Order order;


    public OrderDetail() {
    }

    public OrderDetail(Integer id, String productName, Integer quantity, Integer size, String color, String categories,
            Float unitPrice, Float subtotal, Product product, Order order) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.categories = categories;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.product = product;
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", productName=" + productName + ", quantity=" + quantity + ", size=" + size
                + ", color=" + color + ", categories=" + categories + ", unitPrice=" + unitPrice + ", subtotal="
                + subtotal + ", product=" + product + ", order=" + order + "]";
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
