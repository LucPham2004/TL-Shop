package com.e_shop.Shoe_Shop.dto.request;

import java.util.Set;

public class ProductEditRequest {
    private Integer id;
    private String productName;
    private String productDescription;
    private Float productPrice;
    private Float discountPercent;
    
    private String brandName;
    private Set<String> categories;


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
    public Float getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }
    public Float getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(Float discountPercent) {
        this.discountPercent = discountPercent;
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
}
