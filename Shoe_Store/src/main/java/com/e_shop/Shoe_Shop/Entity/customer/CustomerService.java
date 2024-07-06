package com.e_shop.Shoe_Shop.Entity.customer;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_shop.Shoe_Shop.Entity.role.Role;
import com.e_shop.Shoe_Shop.Entity.role.RoleRepository;

@Service
public class CustomerService implements UserDetailsService{
    private final CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new IllegalStateException("Customer not found!");
        }
        Set<Role> customerRoles = customer.getAuthorities();
        Optional<Role> userRole = roleRepository.findByAuthority("USER");

        if (!customerRoles.contains(userRole.orElseThrow(() -> new IllegalStateException("USER role not found in database")))) {
            customerRoles.add(userRole.get());
            customer.setAuthorities(customerRoles);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer findCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer with id: " + id + " does not exist!"));
        return customer;
    }

    public Customer findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalStateException("Customer with email: " + email + " does not exist!");
        }
        return customer;
    }

    // GET
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        return findCustomerById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return findCustomerByEmail(email);
    }

    // POST
    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new IllegalStateException("Customer with email: " + customer.getEmail() + " already exists!");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    // DELETE
    public void deleteCustomerByIdAndEmail(int id, String email)
    {
        Customer customer = customerRepository.findByIdAndEmail(id, email);
        if(customer == null){
            throw new IllegalStateException("Customer with email: " + email + " and id: " + id + " does not exist!");
        }
        customerRepository.delete(customer);
    }

    // PUT
    @Transactional
    public void UpdateCustomer(int id, String newEmail, String newName, String newPhone, String newAddress)
    {
        Customer customer = findCustomerById(id);

        if(newEmail != null && !newEmail.isBlank() && !Objects.equals(customer.getEmail(), newEmail))
        {
            if(customerRepository.existsByEmail(newEmail))
                throw new IllegalStateException("Email taken! Please use another email!");

            customer.setEmail(newEmail);
        }

        if(newName != null && !newName.isBlank() && !Objects.equals(customer.getName(), newName))
            customer.setName(newName);

        if(newPhone != null && !newPhone.isBlank() && !Objects.equals(customer.getPhone(), newPhone))
            customer.setPhone(newPhone);
        
        if(newAddress != null && !newAddress.isBlank() && !Objects.equals(customer.getAddress(), newAddress))
            customer.setAddress(newAddress);

        customerRepository.save(customer);
    }
}
