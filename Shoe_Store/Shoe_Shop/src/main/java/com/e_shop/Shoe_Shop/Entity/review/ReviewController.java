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

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping(path = "/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }
    
    @GetMapping(path = "/product/{product_id}")
    public List<Review> getAllReviewbyProduct(@PathVariable int product_id)
    {
        return reviewService.getAllReviewsByProduct(product_id);
    }

    @GetMapping(path = "/customer/{customer_id}")
    public List<Review> getAllReviewbyCustomer(@PathVariable int customer_id)
    {
        return reviewService.getAllReviewsByCustomer(customer_id);
    }

    // POST
    @PostMapping
    public void addNewReview(@RequestBody Review review)
    {
        reviewService.addNewReview(review);
    }

    // PUT
    @PutMapping
    public void updateReview(@RequestBody Review review)
    {
        reviewService.updateReview(review);
    }

    // DELETE
    @DeleteMapping
    public void deleteReview(@RequestBody int id)
    {
        reviewService.deleteReview(id);
    }
}
