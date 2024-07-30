package com.e_shop.Shoe_Shop.Entity.customer;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    public CustomerDTO ConvertToDTO(Customer customer) {
        return new CustomerDTO(
            customer.getId(),
            customer.getEmail(),
            customer.getName(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getRoles(),
            customer.getOrder(),
            customer.getReview(),
            customer.isAccountNonExpired(),
            customer.isCredentialsNonExpired(),
            customer.isAccountNonLocked(),
            customer.isEnabled()
        );
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

        Set<Role> customerRoles = customer.getRoles();
        Optional<Role> userRole = roleRepository.findByAuthority("USER");

        if (!customerRoles.contains(userRole.orElseThrow(() -> new IllegalStateException("USER role not found in database")))) {
            customerRoles.add(userRole.get());
            customer.setAuthorities(customerRoles);
            customerRepository.save(customer);
        }

        return new User(customer.getUsername(), customer.getPassword(), getAuthorities(customer.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());
    }

    public CustomerDTO findCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer with id: " + id + " does not exist!"));
        return ConvertToDTO(customer);
    }

    public CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalStateException("Customer with email: " + email + " does not exist!");
        }
        return ConvertToDTO(customer);
    }

    // GET
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
        .map(this::ConvertToDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(int id) {
        return findCustomerById(id);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return findCustomerByEmail(email);
    }

    public List<CustomerDTO> searchCustomer(String keywword) {
        return customerRepository.findByNameContainingOrAddressContaining(keywword, keywword).stream()
        .map(this::ConvertToDTO).collect(Collectors.toList());
    }

    // POST
    public CustomerDTO createCustomer(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new IllegalStateException("Customer with email: " + customer.getEmail() + " already exists!");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return ConvertToDTO(customerRepository.save(customer));
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
        Customer customer = customerRepository.findById(id);

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

    static class UpdateUserInfoRequest {
        private Integer id;
        private String email;
        private String name;
        private String phone;
        private String address;

        @Override
        public String toString() {
            return "UpdateUserInfoRequest [id=" + id + ", email=" + email + ", name=" + name + ", phone=" + phone
                    + ", address=" + address + "]";
        }

        public UpdateUserInfoRequest(Integer id, String email, String name, String phone, String address) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.phone = phone;
            this.address = address;
        }

        public UpdateUserInfoRequest() {
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhone() {
            return phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
    }
}
