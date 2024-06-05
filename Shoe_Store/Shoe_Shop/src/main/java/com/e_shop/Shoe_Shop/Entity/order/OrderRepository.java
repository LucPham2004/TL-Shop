package com.e_shop.Shoe_Shop.Entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
    public Order findByIdAndCustomer(int id,Customer customer);

    public Order deleteByIdAndCustomer(int id,Customer customer);
} 
