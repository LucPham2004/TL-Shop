package com.e_shop.Shoe_Shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    
    public List<Review> findByCustomerId(Integer customer_id);

    public List<Review> findByProductId(Integer product_id);
}
