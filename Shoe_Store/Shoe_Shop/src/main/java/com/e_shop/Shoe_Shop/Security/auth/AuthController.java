package com.e_shop.Shoe_Shop.Security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @SuppressWarnings("null")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        
        if (customer != null && customerService.checkPassword(loginRequest.getPassword(), customer.getPassword())) {
            Cookie loginCookie = new Cookie("userLoggedIn", "true");
            loginCookie.setMaxAge(3600);
            response.addCookie(loginCookie);
            return ResponseEntity.ok("Sign In Success!");
        } 
        else {
            return ResponseEntity.status(401).body("Login failed. Please check the information again.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        if (customerRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(400).body("Email taken!");
        }

        Customer newCustomer = new Customer();
        newCustomer.setName(registerRequest.getName());
        newCustomer.setEmail(registerRequest.getEmail());
        newCustomer.setPhone(registerRequest.getPhone());
        newCustomer.setPassword(registerRequest.getPassword());

        customerService.createCustomer(newCustomer);

        return ResponseEntity.ok("Sign Up Success!");
    }
}
