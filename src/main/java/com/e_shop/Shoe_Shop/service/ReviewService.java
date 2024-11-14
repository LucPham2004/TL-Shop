package com.e_shop.Shoe_Shop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.dto.dto.ReviewDTO;
import com.e_shop.Shoe_Shop.dto.request.ReviewCreationRequest;
import com.e_shop.Shoe_Shop.entity.Customer;
import com.e_shop.Shoe_Shop.entity.Product;
import com.e_shop.Shoe_Shop.entity.Review;
import com.e_shop.Shoe_Shop.exception.AppException;
import com.e_shop.Shoe_Shop.exception.ErrorCode;
import com.e_shop.Shoe_Shop.repository.CustomerRepository;
import com.e_shop.Shoe_Shop.repository.ProductRepository;
import com.e_shop.Shoe_Shop.repository.ReviewRepository;

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
        return convertToDTO(reviewRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED)));
    }

    public List<ReviewDTO> getAllReviewsByProduct(int product_id)
    {
        Product product = productRepository.findById(product_id);
        if(product == null)
        {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
        }
        return reviewRepository.findByProductId(product_id)
        .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTO> getAllReviewsByCustomer(int customer_id)
    {
        Customer customer = customerRepository.findById(customer_id);
        if(customer == null)
        {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
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
        .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));

        reviewToUpdate.setReviewTitle(reviewCreationRequest.getReviewTitle());
        reviewToUpdate.setReviewContent(reviewCreationRequest.getReviewContent());
        reviewToUpdate.setReviewRating(reviewCreationRequest.getReviewRating());

        return convertToDTO(reviewRepository.save(reviewToUpdate));
    }
}
