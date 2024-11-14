package com.e_shop.Shoe_Shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    public Optional<Role> findByAuthority(String authority);

    public boolean existsByAuthority(String authority);
}
