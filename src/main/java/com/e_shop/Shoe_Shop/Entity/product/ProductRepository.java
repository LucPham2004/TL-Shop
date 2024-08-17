package com.e_shop.Shoe_Shop.Entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

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