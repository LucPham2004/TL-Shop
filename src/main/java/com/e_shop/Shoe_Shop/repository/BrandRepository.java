package com.e_shop.Shoe_Shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    public boolean existsByName(String name);

    public Brand findByName(String name);

    public Brand findById(int id);
}
