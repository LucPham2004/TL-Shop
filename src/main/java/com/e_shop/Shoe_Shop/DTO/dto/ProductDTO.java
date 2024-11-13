package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;
    private String productDescription;
    private String productImage;
    private Float productPrice;
    private Integer productQuantity;
    private Integer productQuantitySold;
    private Date productDayCreated;
    private Float discountPercent;
    private Integer reviewCount;
    private Float averageRating;
    private String brandName;
    private Set<String> categories;
    private Set<ProductDetailDTO> details;
    
    private String[] urls;
    private String[] publicIds;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String productName, String productDescription, String productImage,
            Float productPrice, Integer productQuantity, Integer productQuantitySold, Date productDayCreated,
            Float discountPercent, Integer reviewCount, Float averageRating, String brandName, Set<String> categories,
            Set<ProductDetailDTO> details, String[] urls, String[] publicIds) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productQuantitySold = productQuantitySold;
        this.productDayCreated = productDayCreated;
        this.discountPercent = discountPercent;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.brandName = brandName;
        this.categories = categories;
        this.details = details;
        this.urls = urls;
        this.publicIds = publicIds;
    }

    // Inner class for ProductDetailDTO
    public static class ProductDetailDTO {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String color;
        private Integer size;
        private Integer quantity;
        private Integer quantitySold;

        public ProductDetailDTO() {
        }

        public ProductDetailDTO(Integer id, String color, Integer size, Integer quantity, Integer quantitySold) {
            this.id = id;
            this.color = color;
            this.size = size;
            this.quantity = quantity;
            this.quantitySold = quantitySold;
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

        public Integer getQuantitySold() {
            return quantitySold;
        }

        public void setQuantitySold(Integer quantitySold) {
            this.quantitySold = quantitySold;
        }
    }

    @Override
    public String toString() {
        return "ProductDTO [id=" + id + ", productName=" + productName + ", productDescription=" + productDescription
                + ", productImage=" + productImage + ", productPrice=" + productPrice + ", productQuantity="
                + productQuantity + ", productQuantitySold=" + productQuantitySold + ", productDayCreated="
                + productDayCreated + ", discountPercent=" + discountPercent + ", reviewCount=" + reviewCount
                + ", averageRating=" + averageRating + ", brandName=" + brandName + ", categories=" + categories
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

    public Set<ProductDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(Set<ProductDetailDTO> details) {
        this.details = details;
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
