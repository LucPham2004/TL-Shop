package com.e_shop.Shoe_Shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.ProductDetail;;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    
}
