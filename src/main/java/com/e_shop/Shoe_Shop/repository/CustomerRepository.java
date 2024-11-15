package com.e_shop.Shoe_Shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_shop.Shoe_Shop.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Optional<Customer> findByEmail(String email);

    public Optional<Customer> findById(int id);

    public Optional<Customer> findByName(String name);
    
    public List<Customer> findByNameContainingOrAddressContainingOrEmailContainingOrPhoneContaining(
        String nameKeyword, String addressKeyword, String emailKeyword, String phoneKeyword);

    @Query("SELECT c FROM Customer c " + "WHERE (:id IS NULL OR c.id = :id) ")
    public List<Customer> findByIdContaining(@Param("id") Integer id);

    public Optional<Customer> findByIdAndEmail(int id, String email);

    @Modifying
    @Query("UPDATE Customer c SET c.password = :password WHERE c.id = :customerId")
    void changeCustomerPassword(@Param("customerId") int customerId, @Param("password") String password);

    public boolean existsByEmail(String email);

    public boolean existsByName(String name);

    @Query(value = "SELECT * FROM Customer c WHERE c.refresh_token = :token AND (c.email = :email)", nativeQuery = true)
     Optional<Customer> findByRefreshTokenAndEmailOrUsernameOrPhone(@Param("token") String token,
               @Param("email") String email);
}
