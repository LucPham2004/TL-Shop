package com.e_shop.Shoe_Shop.Entity.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    public Optional<Role> findByAuthority(String authority);
}
