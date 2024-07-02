package com.e_shop.Shoe_Shop.Entity.review;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Entity.review.ReviewService.NewReviewRequest;
import com.e_shop.Shoe_Shop.Entity.review.ReviewService.ReviewResponse;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET
    @GetMapping
    public List<ReviewResponse> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping(path = "/{id}")
    public ReviewResponse getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }
    
    @GetMapping(path = "/product/{product_id}")
    public List<ReviewResponse> getAllReviewbyProduct(@PathVariable int product_id) {
        return reviewService.getAllReviewsByProduct(product_id);
    }

    @GetMapping(path = "/customer/{customer_id}")
    public List<ReviewResponse> getAllReviewbyCustomer(@PathVariable int customer_id) {
        return reviewService.getAllReviewsByCustomer(customer_id);
    }

    // POST
    @PostMapping
    public void addNewReview(@RequestBody NewReviewRequest newReviewRequest) {
        reviewService.addNewReview(newReviewRequest);
    }

    // PUT
    @PutMapping
    public void updateReview(@RequestBody NewReviewRequest newReviewRequest) {
        reviewService.updateReview(newReviewRequest);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
