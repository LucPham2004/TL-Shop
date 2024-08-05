package com.e_shop.Shoe_Shop.Entity.customer;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);

    public Customer findById(int id);

    public Customer findByName(String name);
    
    public List<Customer> findByNameContainingOrAddressContaining(String nameKeyword, String addressKeyword);

    public Customer findByIdAndEmail(int id, String email);

    @Modifying
    @Query("UPDATE Customer c SET c.password = :password WHERE c.id = :customerId")
    void changeCustomerPassword(@Param("customerId") int customerId, @Param("password") String password);

    public boolean existsByEmail(String email);

    public boolean existsByName(String name);
}
