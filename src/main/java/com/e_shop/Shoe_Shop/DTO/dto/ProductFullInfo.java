package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Date;
import java.util.Set;

public class ProductFullInfo {
    private Integer id;
    private String productName;
    private String productDescription;
    private String productImage;
    private Float productPrice;
    private Float discountPercent;
    private Integer productQuantity;
    private Integer productQuantitySold;
    private Date productDayCreated;
    private Integer reviewCount;
    private Float averageRating;
    
    private String brandName;
    private Set<String> categories;
    
    private String[] urls;
    private String[] publicIds;
    
    public ProductFullInfo() {
    }
    public ProductFullInfo(Integer id, String productName, String productDescription, String productImage,
            Float productPrice, Float discountPercent, Integer productQuantity, Integer productQuantitySold,
            Date productDayCreated, Integer reviewCount, Float averageRating, String brandName, Set<String> categories,
            String[] urls, String[] publicIds) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.discountPercent = discountPercent;
        this.productQuantity = productQuantity;
        this.productQuantitySold = productQuantitySold;
        this.productDayCreated = productDayCreated;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.brandName = brandName;
        this.categories = categories;
        this.urls = urls;
        this.publicIds = publicIds;
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
    public Float getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(Float discountPercent) {
        this.discountPercent = discountPercent;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    public Integer getProductQuantitySold() {
        return productQuantitySold;
    }
    public void setProductQuantitySold(Integer productQuantitySold) {
        this.productQuantitySold = productQuantitySold;
    }
    public Date getProductDayCreated() {
        return productDayCreated;
    }
    public void setProductDayCreated(Date productDayCreated) {
        this.productDayCreated = productDayCreated;
    }
    public Integer getReviewCount() {
        return reviewCount;
    }
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
    public Float getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
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
    public String[] getUrls() {
        return urls;
    }
    public void setUrls(String[] urls) {
        this.urls = urls;
    }
    public String[] getPublicIds() {
        return publicIds;
    }
    public void setPublicIds(String[] publicIds) {
        this.publicIds = publicIds;
    }
}
