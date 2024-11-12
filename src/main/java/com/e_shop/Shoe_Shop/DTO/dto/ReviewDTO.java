package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Date;

public class ReviewDTO {
    private int id;
    private String customerName;
    private Date reviewDate;
    private String reviewTitle;
    private String reviewContent;
    private Integer reviewRating;
    
    public ReviewDTO(int id, String customerName, Date reviewDate, String reviewTitle, String reviewContent,
            Integer reviewRating) {
        this.id = id;
        this.customerName = customerName;
        this.reviewDate = reviewDate;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }
    public ReviewDTO() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
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
    public Integer getReviewRating() {
        return reviewRating;
    }
    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }
}
