package com.e_shop.Shoe_Shop.DTO.request;

import java.util.Date;

public class ReviewCreationRequest {
    private int id;
    private String reviewTitle;
    private String reviewContent;
    private Date reviewDate;
    private Integer reviewRating;
    private int productId;
    private int customerId;
    
    public ReviewCreationRequest(int id, String reviewTitle, String reviewContent, Date reviewDate,
        Integer reviewRating, int productId, int customerId) {
        this.id = id;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
        this.reviewRating = reviewRating;
        this.productId = productId;
        this.customerId = customerId;
    }
    public ReviewCreationRequest() {
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getReviewTitle() {
        return reviewTitle;
    }
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }
    public String getReviewContent() {
        return reviewContent;
    }
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    public Integer getReviewRating() {
        return reviewRating;
    }
    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
