package com.e_shop.Shoe_Shop.Entity.review;

import java.util.*;
import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reivewRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public ReviewService(ReviewRepository reivewRepository) {
        this.reivewRepository = reivewRepository;
    }

    // GET
    public List<Review> getAllReviews() {
        return reivewRepository.findAll();
    }

    public Review getReviewById(int id) {
        return reivewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<Review> getAllReviewsByProduct(int product_id)
    {
        boolean exists = productRepository.existsById(product_id);
        if(!exists)
        {
            throw new IllegalStateException("Product with id: " + product_id + " does not exist!");
        }
        return reivewRepository.findByProductId(product_id);
    }

    public List<Review> getAllReviewsByCustomer(int customer_id)
    {
        boolean exists = customerRepository.existsById(customer_id);
        if(!exists)
        {
            throw new IllegalStateException("Customer with id: " + customer_id + " does not exist!");
        }
        return reivewRepository.findByCustomerId(customer_id);
    }

    // POST
    public Review addNewReview(Review review) {
        return reivewRepository.save(review);
    }

    // DELETE
    public void deleteReview(int id) {
        reivewRepository.deleteById(id);
    }

    // PUT
    @Transactional
    public void updateReview(Review review) {
        Review reviewToUpdate = reivewRepository.findById(review.getId())
        .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewToUpdate = review;
        reivewRepository.save(reviewToUpdate);
    }
}
