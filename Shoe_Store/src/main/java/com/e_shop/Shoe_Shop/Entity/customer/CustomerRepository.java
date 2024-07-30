package com.e_shop.Shoe_Shop.Entity.customer;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);

    public Customer findById(int id);

    public Customer findByName(String name);
    
    public List<Customer> findByNameContainingOrAddressContaining(String nameKeyword, String addressKeyword);

    public Customer findByIdAndEmail(int id, String email);

    public boolean existsByEmail(String email);
}
