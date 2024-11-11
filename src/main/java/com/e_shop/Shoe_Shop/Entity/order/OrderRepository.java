package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>{

    public Order findById(int id);

    public Order save(Order order);
    
    public void delete(Order order);

    public boolean existsById(Integer id);

    public Page<Order> findByCustomerId(int customerId, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId " +
              "ORDER BY CASE WHEN o.status = 'Processing' THEN 1 " +
              "WHEN o.status = 'Delivering' THEN 2 " +
              "WHEN o.status = 'Completed' THEN 3 " +
              "WHEN o.status = 'Cancelled' THEN 4 " +
              "ELSE 5 END")
    Page<Order> findByCustomerIdSorted(@Param("customerId") int customerId, Pageable pageable);
    
    @Query("SELECT o FROM Order o " +
              "ORDER BY CASE WHEN o.status = 'Processing' THEN 1 " +
              "WHEN o.status = 'Delivering' THEN 2 " +
              "WHEN o.status = 'Completed' THEN 3 " +
              "WHEN o.status = 'Cancelled' THEN 4 " +
              "ELSE 5 END")
    Page<Order> findOrdersSorted(Pageable pageable);
    
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

    @Query("SELECT SUM(o.total) FROM Order o " + "WHERE o.status = 'Completed' ")
    public Float getTotalRevenue();
} 
