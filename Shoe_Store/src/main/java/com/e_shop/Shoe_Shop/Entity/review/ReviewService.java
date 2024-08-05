package com.e_shop.Shoe_Shop.Entity.review;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.product.Product;
import com.e_shop.Shoe_Shop.Entity.product.ProductRepository;
import com.e_shop.Shoe_Shop.DTO.dto.ReviewDTO;
import com.e_shop.Shoe_Shop.DTO.request.ReviewCreationRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository,
            ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public ReviewDTO convertToDTO(Review review) {
        Customer customer = review.getCustomer();
        ReviewDTO reviewDto = new ReviewDTO();
        reviewDto.setId(review.getId());
        reviewDto.setCustomerName(customer.getName());
        reviewDto.setReviewContent(review.getReviewContent());
        reviewDto.setReviewDate(review.getReviewDate());
        reviewDto.setReviewRating(review.getReviewRating());
        reviewDto.setReviewTitle(review.getReviewTitle());
        
        return reviewDto;
    }

    // GET
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(int id) {
        return convertToDTO(reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found")));
    }

    public List<ReviewDTO> getAllReviewsByProduct(int product_id)
    {
        Product product = productRepository.findById(product_id);
        if(product == null)
        {
            throw new IllegalStateException("Product with id: " + product_id + " does not exist!");
        }
        return reviewRepository.findByProductId(product_id)
        .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTO> getAllReviewsByCustomer(int customer_id)
    {
        Customer customer = customerRepository.findById(customer_id);
        if(customer == null)
        {
            throw new IllegalStateException("Customer with id: " + customer_id + " does not exist!");
        }
        return reviewRepository.findByCustomerId(customer_id)
        .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // POST
    public ReviewDTO addNewReview(ReviewCreationRequest reviewCreationRequest) {
    
        Product product = productRepository.findById(reviewCreationRequest.getProductId());
        Customer customer = customerRepository.findById(reviewCreationRequest.getCustomerId());

        Review newReview = new Review();
        newReview.setReviewContent(reviewCreationRequest.getReviewContent());
        newReview.setReviewDate(reviewCreationRequest.getReviewDate());
        newReview.setReviewRating(reviewCreationRequest.getReviewRating());
        newReview.setReviewTitle(reviewCreationRequest.getReviewTitle());
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

        return convertToDTO(reviewSave);
    }

    // DELETE
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    // PUT
    @Transactional
    public ReviewDTO updateReview(ReviewCreationRequest reviewCreationRequest) {
        Review reviewToUpdate = reviewRepository.findById(reviewCreationRequest.getId())
        .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        reviewToUpdate.setReviewTitle(reviewCreationRequest.getReviewTitle());
        reviewToUpdate.setReviewContent(reviewCreationRequest.getReviewContent());
        reviewToUpdate.setReviewRating(reviewCreationRequest.getReviewRating());

        return convertToDTO(reviewRepository.save(reviewToUpdate));
    }
}
