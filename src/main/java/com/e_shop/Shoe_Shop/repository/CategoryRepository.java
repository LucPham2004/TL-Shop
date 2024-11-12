package com.e_shop.Shoe_Shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public boolean existsByName(String name);

    public Category findByName(String Name);

    public Category findById(int id);
}
