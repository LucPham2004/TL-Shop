package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductInfoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;
    private String productDescription;
    private String productImage;
    private Float productPrice;
    private Float discountPercent;
    private String brandName;
    private Set<String> categories;

    public ProductInfoDTO(Integer id, String productName, String productDescription, String productImage,
            Float productPrice, Float discountPercent, String brandName, Set<String> categories) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.discountPercent = discountPercent;
        this.brandName = brandName;
        this.categories = categories;
    }
    public ProductInfoDTO() {
    }
    @Override
    public String toString() {
        return "ProductInfoDTO [id=" + id + ", productName=" + productName + ", productDescription="
                + productDescription + ", productImage=" + productImage + ", productPrice=" + productPrice
                + ", discountPercent=" + discountPercent + ", brandName=" + brandName + ", categories=" + categories
                + "]";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public Float getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public Set<String> getCategories() {
        return categories;
    }
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
    public Float getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(Float discountPercent) {
        this.discountPercent = discountPercent;
    }
}
