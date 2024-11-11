package com.e_shop.Shoe_Shop.DTO.dto.admin;

public class ProductSummary {
    private long totalProducts;
    private long totalCategories;
	private long totalBrands;
	private long totalReviews;
	
	private long enabledProductsCount;
	private long disabledProductsCount;
    
	private long reviewedProductsCount;

    public ProductSummary() {
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalBrands() {
        return totalBrands;
    }

    public void setTotalBrands(long totalBrands) {
        this.totalBrands = totalBrands;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getEnabledProductsCount() {
        return enabledProductsCount;
    }

    public void setEnabledProductsCount(long enabledProductsCount) {
        this.enabledProductsCount = enabledProductsCount;
    }

    public long getDisabledProductsCount() {
        return disabledProductsCount;
    }

    public void setDisabledProductsCount(long disabledProductsCount) {
        this.disabledProductsCount = disabledProductsCount;
    }

    public long getReviewedProductsCount() {
        return reviewedProductsCount;
    }

    public void setReviewedProductsCount(long reviewedProductsCount) {
        this.reviewedProductsCount = reviewedProductsCount;
    }

}
