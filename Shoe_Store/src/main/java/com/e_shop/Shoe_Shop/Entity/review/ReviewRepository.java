package com.e_shop.Shoe_Shop.Entity.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    
    // @Query("select r from Review r where r.product.id = ?1")
    // public List<Review> findByProductId(Integer product_id);

    // @Query("select r from Review r where r.customer.id = ?1")
    // public List<Review> findByCustomerId(Integer customer_id);
    
    public List<Review> findByCustomerId(Integer customer_id);

    public List<Review> findByProductId(Integer product_id);
}
