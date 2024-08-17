package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

    public Order findById(int id);

    public List<Order> findByCustomerId(int customerId);
    
    public Order findByIdAndCustomer(int id, Customer customer);

    @Query("SELECT o FROM Order o " +
           "JOIN o.customer c " +
           "JOIN o.orderDetails od " +
           "WHERE (:status IS NULL OR o.status LIKE %:status%) " +
           "OR (:customerName IS NULL OR c.name LIKE %:customerName%) " +
           "OR (:productName IS NULL OR od.productName LIKE %:productName%)")
    List<Order> findByStatusContainingOrCustomerNameContainingOrProductNameContaining(
            @Param("status") String status, 
            @Param("customerName") String customerName, 
            @Param("productName") String productName);

    @Query("SELECT o FROM Order o " + "WHERE (:id IS NULL OR o.id = :id) ")
    public List<Order> findByIdContaining(@Param("id") Integer id);

    public boolean existsByIdAndCustomer(int id, Customer customer);
} 
