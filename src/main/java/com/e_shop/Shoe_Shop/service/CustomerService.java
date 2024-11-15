package com.e_shop.Shoe_Shop.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_shop.Shoe_Shop.dto.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.dto.request.ChangePasswordRequest;
import com.e_shop.Shoe_Shop.entity.Customer;
import com.e_shop.Shoe_Shop.exception.AppException;
import com.e_shop.Shoe_Shop.exception.ErrorCode;
import com.e_shop.Shoe_Shop.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomerService{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    public CustomerService(CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
            customer.getId(),
            customer.getEmail(),
            customer.getName(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getDayCreated(),
            customer.getRoles(),
            customer.getOrder(),
            customer.getReview(),
            customer.isAccountNonLocked(),
            customer.isEnabled()
        );
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    

    public Customer handleGetCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
    }

    public CustomerDTO findCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
        return convertToDTO(customer);
    }

    public CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
        return convertToDTO(customer);
    }

    // GET
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
        .map(this::convertToDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(int id) {
        return findCustomerById(id);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return findCustomerByEmail(email);
    }

    public List<CustomerDTO> searchCustomer(String keywword) {
        return customerRepository.findByNameContainingOrAddressContainingOrEmailContainingOrPhoneContaining(
            keywword, keywword, keywword, keywword).stream()
        .map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CustomerDTO> searchCustomerByIdContaining(int id) {
        return customerRepository.findByIdContaining(id).stream()
        .map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CustomerDTO> newCustomers() {
        List<CustomerDTO> new_customers = customerRepository.findAll().stream()
        .map(this::convertToDTO).collect(Collectors.toList());

        Collections.sort(new_customers, 
            (CustomerDTO order1, CustomerDTO order2) -> order2.getDayCreated().compareTo(order1.getDayCreated()));
        
        return new_customers;
    }

    // POST
    
    // Create Customer
    public CustomerDTO createCustomer(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new AppException(ErrorCode.ENTITY_EXISTED);
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return convertToDTO(customerRepository.save(customer));
    }

    // Change Customer password
    @Transactional
    public CustomerDTO ChangePassword(ChangePasswordRequest changePasswordRequest, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(auth);

            Customer customer = customerRepository.findById(changePasswordRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));

            if(customer == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            boolean checkPassword = checkPassword(changePasswordRequest.getOldPassword(), customer.getPassword());

            String newPassword = changePasswordRequest.getNewPassword();
            if(newPassword != null && newPassword.length() > 0 && newPassword.equals(changePasswordRequest.getOldPassword()) && checkPassword) {
                customerRepository.changeCustomerPassword(changePasswordRequest.getId(), passwordEncoder.encode(newPassword));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            return convertToDTO(customer);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed for email: " + changePasswordRequest.getEmail());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    // DELETE
    public void deleteCustomerByIdAndEmail(int id, String email)
    {
        Customer customer = customerRepository.findByIdAndEmail(id, email)
        .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
        customerRepository.delete(customer);
    }

    // PUT
    @Transactional
    public CustomerDTO UpdateCustomer(int id, String newEmail, String newName, String newPhone, String newAddress)
    {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));

        if(newEmail != null && !newEmail.isBlank() && !Objects.equals(customer.getEmail(), newEmail))
        {
            if(customerRepository.existsByEmail(newEmail))
                throw new AppException(ErrorCode.ENTITY_EXISTED);

            customer.setEmail(newEmail);
        }

        if(newName != null && !newName.isBlank() && !Objects.equals(customer.getName(), newName))
            customer.setName(newName);

        if(newPhone != null && !newPhone.isBlank() && !Objects.equals(customer.getPhone(), newPhone))
            customer.setPhone(newPhone);
        
        if(newAddress != null && !newAddress.isBlank() && !Objects.equals(customer.getAddress(), newAddress))
            customer.setAddress(newAddress);

        return convertToDTO(customerRepository.save(customer));
    }

    public void updateUserToken(String token, String email) {
        Customer currentUser = this.handleGetCustomerByEmail(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.customerRepository.save(currentUser);
        }
    }

    public Customer getUserByRefreshTokenAndEmailOrUsernameOrPhone(String token, String emailUsernamePhone) {
        return this.customerRepository.findByRefreshTokenAndEmailOrUsernameOrPhone(token, emailUsernamePhone)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
    }
}
