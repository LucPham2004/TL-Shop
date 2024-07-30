package com.e_shop.Shoe_Shop.Entity.review;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.product.Product;
import com.e_shop.Shoe_Shop.Entity.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse convertToResponse(Review review) {
        Customer customer = review.getCustomer();
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setCustomerName(customer.getName());
        reviewResponse.setReviewContent(review.getReviewContent());
        reviewResponse.setReviewDate(review.getReviewDate());
        reviewResponse.setReviewRating(review.getReviewRating());
        reviewResponse.setReviewTitle(review.getReviewTitle());
        
        return reviewResponse;
    }

    // GET
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(int id) {
        return convertToResponse(reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found")));
    }

    public List<ReviewResponse> getAllReviewsByProduct(int product_id)
    {
        Product product = productRepository.findById(product_id);
        if(product == null)
        {
            throw new IllegalStateException("Product with id: " + product_id + " does not exist!");
        }
        return reviewRepository.findByProductId(product_id)
        .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public List<ReviewResponse> getAllReviewsByCustomer(int customer_id)
    {
        Customer customer = customerRepository.findById(customer_id);
        if(customer == null)
        {
            throw new IllegalStateException("Customer with id: " + customer_id + " does not exist!");
        }
        return reviewRepository.findByCustomerId(customer_id)
        .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    // POST
    public ReviewResponse addNewReview(NewReviewRequest newReviewRequest) {
    
        Product product = productRepository.findById(newReviewRequest.getProductId());
        Customer customer = customerRepository.findById(newReviewRequest.getCustomerId());

        Review newReview = new Review();
        newReview.setReviewContent(newReviewRequest.getReviewContent());
        newReview.setReviewDate(newReviewRequest.getReviewDate());
        newReview.setReviewRating(newReviewRequest.getReviewRating());
        newReview.setReviewTitle(newReviewRequest.getReviewTitle());
        newReview.setCustomer(customer);

        Set<Review> reviews = product.getReviews();
        Float avgRating = 0.0f;
        for(Review review: reviews) {
            avgRating += review.getReviewRating();
        }
        if (!reviews.isEmpty()) {
            avgRating /= reviews.size();
        }
        
        newReview.setProduct(product);

        Review reviewSave = reviewRepository.save(newReview);
        product.setAverageRating(product.getAverageRating());
        product.setReviewCount(product.getReviews().size());
        productRepository.save(product);

        return convertToResponse(reviewSave);
    }

    // DELETE
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    // PUT
    @Transactional
    public ReviewResponse updateReview(NewReviewRequest newReviewRequest) {
        Review reviewToUpdate = reviewRepository.findById(newReviewRequest.getId())
        .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        reviewToUpdate.setReviewTitle(newReviewRequest.getReviewTitle());
        reviewToUpdate.setReviewContent(newReviewRequest.getReviewContent());
        reviewToUpdate.setReviewRating(newReviewRequest.getReviewRating());

        return convertToResponse(reviewRepository.save(reviewToUpdate));
    }

    static class NewReviewRequest {
        private int id;
        private String reviewTitle;
        private String reviewContent;
        private Date reviewDate;
        private Integer reviewRating;
        private int productId;
        private int customerId;
        
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

    static class ReviewResponse {
        private int id;
        private String customerName;
        private Date reviewDate;
        private String reviewTitle;
        private String reviewContent;
        private Integer reviewRating;

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
}
