package com.e_shop.Shoe_Shop.Entity.product;

import java.util.Set;


public class ProductDTO {

    private Integer id;
    private String productName;
    private String productDescription;
    private String productImage;
    private Float productPrice;
    private Integer productQuantity;
    private Float discountPercent;
    private Integer reviewCount;
    private Float averageRating;
    private int brandId;
    private Set<String> categories;
    private Set<ProductDetailDTO> details;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String productName, String productDescription, String productImage,
            Float productPrice, Integer productQuantity, Float discountPercent, Integer reviewCount,
            Float averageRating, int brandId, Set<String> categories, Set<ProductDetailDTO> details) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.discountPercent = discountPercent;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.brandId = brandId;
        this.categories = categories;
        this.details = details;
    }

    // Inner class for ProductDetailDTO
    public static class ProductDetailDTO {

        private Integer id;
        private String color;
        private Integer size;
        private Integer quantity;

        public ProductDetailDTO() {
        }
        
        public ProductDetailDTO(Integer id, String color, Integer size, Integer quantity) {
            this.id = id;
            this.color = color;
            this.size = size;
            this.quantity = quantity;
        }
        
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

        @Override
        public String toString() {
            return "ProductDetailDTO [id=" + id + ", color=" + color + ", size=" + size + ", quantity=" + quantity
                    + "]";
        }
    }
        
    @Override
    public String toString() {
        return "ProductDTO [id=" + id + ", productName=" + productName + ", productDescription=" + productDescription
                + ", productImage=" + productImage + ", productPrice=" + productPrice + ", productQuantity="
                + productQuantity + ", discountPercent=" + discountPercent + ", reviewCount=" + reviewCount
                + ", averageRating=" + averageRating + ", brandId=" + brandId + ", categories=" + categories
                + ", details=" + details + "]";
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

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Float discountPercent) {
        this.discountPercent = discountPercent;
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

    public int getBrandId() {
        return brandId;
    }

    public void setBrandName(int brandId) {
        this.brandId = brandId;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public Set<ProductDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(Set<ProductDetailDTO> details) {
        this.details = details;
    }
}
