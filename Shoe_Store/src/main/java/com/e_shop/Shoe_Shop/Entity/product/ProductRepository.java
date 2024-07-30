package com.e_shop.Shoe_Shop.Entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    public Product findById(int id);

    public List<Product> findByProductNameContainingOrProductDescriptionContainingOrCategoryName(
        String nameKeyword, String descriptionKeyword, String categoryKeyword);

    public List<Product> findByBrandName(String brandName);

    public List<Product> findByCategoryName(String categoryName);

    public boolean existsByProductNameAndProductDescription(String productName, String productDescription);
}