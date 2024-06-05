package com.e_shop.Shoe_Shop.Entity.product.detail;

import com.e_shop.Shoe_Shop.Entity.product.Product;
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
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Integer id;
    
    private  String color;
    private Integer size;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public String toString() {
        return "ProductDetail [id=" + id + ", color=" + color + ", size=" + size + ", quantity=" + quantity
                + ", product=" + product + "]";
    }

    public ProductDetail() {
    }

    public ProductDetail(String color, Integer size, Integer quantity, Product product) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.product = product;
    }
}
