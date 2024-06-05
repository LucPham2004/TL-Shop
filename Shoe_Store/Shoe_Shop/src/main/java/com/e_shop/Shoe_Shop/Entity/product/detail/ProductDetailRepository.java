package com.e_shop.Shoe_Shop.Entity.product.detail;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    
}
