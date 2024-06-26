package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

    public Order findById(int id);

    public List<Order> findByCustomerId(int customerId);
    
    public Order findByIdAndCustomer(int id, Customer customer);

    public boolean existsByIdAndCustomer(int id, Customer customer);
} 
