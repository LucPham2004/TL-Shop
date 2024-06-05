package com.e_shop.Shoe_Shop.Entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.Entity.category.Category;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    public Product findById(int product_id);

    public List<Product> findByBrandId(int brandId);

    public List<Product> findByCategory(Category category);
}