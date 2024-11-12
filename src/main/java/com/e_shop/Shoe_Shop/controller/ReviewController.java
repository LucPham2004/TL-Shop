package com.e_shop.Shoe_Shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.dto.dto.ReviewDTO;
import com.e_shop.Shoe_Shop.dto.request.ReviewCreationRequest;
import com.e_shop.Shoe_Shop.service.ReviewService;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET
    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping(path = "/{id}")
    public ReviewDTO getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }
    
    @GetMapping(path = "/product/{product_id}")
    public List<ReviewDTO> getAllReviewbyProduct(@PathVariable int product_id) {
        return reviewService.getAllReviewsByProduct(product_id);
    }

    @GetMapping(path = "/customer/{customer_id}")
    public List<ReviewDTO> getAllReviewbyCustomer(@PathVariable int customer_id) {
        return reviewService.getAllReviewsByCustomer(customer_id);
    }

    // POST
    @PostMapping
    public ReviewDTO addNewReview(@RequestBody ReviewCreationRequest reviewCreationRequest) {
        return reviewService.addNewReview(reviewCreationRequest);
    }

    // PUT
    @PutMapping
    public ReviewDTO updateReview(@RequestBody ReviewCreationRequest reviewCreationRequest) {
        return reviewService.updateReview(reviewCreationRequest);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
