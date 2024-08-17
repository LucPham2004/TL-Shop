package com.e_shop.Shoe_Shop.Entity.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public boolean existsByName(String name);

    public Category findByName(String Name);

    public Category findById(int id);
}
