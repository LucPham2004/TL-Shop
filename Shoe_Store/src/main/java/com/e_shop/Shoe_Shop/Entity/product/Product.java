package com.e_shop.Shoe_Shop.Entity.product;

import java.util.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.e_shop.Shoe_Shop.Entity.category.Category;
import com.e_shop.Shoe_Shop.Entity.product.detail.ProductDetail;
import com.e_shop.Shoe_Shop.Entity.brand.Brand;
import com.e_shop.Shoe_Shop.Entity.review.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_price")
    private Float productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "product_quantity_sold")
    private Integer productQuantitySold = 0;

    @Column(name = "day_created")
    private Date productDayCreated;

    @Column(name = "discount_percent")
	private Float discountPercent;

    @Column(name = "review_count")
    private Integer reviewCount = 0;

    @Column(name = "avg_rating")
	private Float averageRating = 0.0f;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id")	
	private Brand brand;

    @ManyToMany
	@JoinTable( name = "product_categories", 
                joinColumns = @JoinColumn(name = "product_id"), 
                inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> category;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "productReference1")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "productReference2")
    private Set<ProductDetail> details;

    @Transient private boolean reviewedByCustomer;

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
        productQuantity = 0;
        for(ProductDetail detail: details) {
            productQuantity += detail.getQuantity();
        }
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getProductQuantitySold() {
        productQuantitySold = 0;
        for(ProductDetail detail: details) {
            productQuantitySold += detail.getQuantitySold();
        }
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

    public Float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Float discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Transient
	public Float getDiscountPrice() {
		if (discountPercent > 0) 
        {
			return productPrice * ((100 - discountPercent) / 100);
		}
		return this.productPrice;
	}

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    
    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<ProductDetail> details) {
        this.details = details;
    }

    public Integer getReviewCount() {
        return reviews.size();
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Float getAverageRating() {
        averageRating = 0.0f;
        for(Review review: reviews) {
            averageRating += review.getReviewRating();
        }
        if(reviews.size() > 0)
            averageRating /= reviews.size();
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isReviewedByCustomer() {
        return reviewedByCustomer = (this.reviews != null) ? true : false;
    }

    public void setReviewedByCustomer(boolean reviewedByCustomer) {
        this.reviewedByCustomer = reviewedByCustomer;
    }

    // Constructor
    public Product() {
    }
       
    public Product(Integer id, String productName, String productDescription, String productImage, Float productPrice,
            Integer productQuantity, Integer productQuantitySold, Date productDayCreated, Float discountPercent, Integer reviewCount, Float averageRating, Brand brand,
            Set<Category> category, Set<Review> reviews, Set<ProductDetail> details, boolean reviewedByCustomer) {
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
        this.brand = brand;
        this.category = category;
        this.reviews = reviews;
        this.details = details;
        this.reviewedByCustomer = reviewedByCustomer;
    }
    
    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + ", productPrice=" + productPrice+ 
                ", productQuantity=" + productQuantity + ", productQuantitySold=" + productQuantitySold + 
                ", discountPercent=" + discountPercent + ", category="+ category + ", brand=" + brand + 
                ", reviewCount=" + reviewCount + ", averageRating=" + averageRating + ", day-created=" + productDayCreated +"]";
    }

}