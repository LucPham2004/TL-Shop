package com.e_shop.Shoe_Shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    public Product findById(int id);

    public List<Product> findByProductNameContainingOrProductDescriptionContainingOrCategoryNameOrBrandNameContaining(
        String nameKeyword, String descriptionKeyword, String categoryKeyword, String brandKeyword);

    @Query("SELECT p FROM Product p " + "WHERE (:id IS NULL OR p.id = :id) ")
    public List<Product> findByIdContaining(@Param("id") Integer id);

    public List<Product> findByBrandName(String brandName);

    public List<Product> findByCategoryName(String categoryName);

    public boolean existsByProductNameAndProductDescription(String productName, String productDescription);
}