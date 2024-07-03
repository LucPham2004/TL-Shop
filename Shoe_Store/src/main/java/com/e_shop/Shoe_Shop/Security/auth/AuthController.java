package com.e_shop.Shoe_Shop.Security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

            System.out.println(Integer.toString(customer.getId()));
            Cookie idCookie = new Cookie("id", Integer.toString(customer.getId()));
            idCookie.setPath("/"); 

            Cookie loginCookie = new Cookie("userLoggedIn", "true");
            loginCookie.setPath("/"); 

            response.addCookie(idCookie);
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userLoggedIn") || cookie.getName().equals("id")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        return ResponseEntity.ok("Logged out successfully!");
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> checkLoginStatus(HttpServletRequest request) {
        
        Cookie[] cookies = request.getCookies();
        boolean isLoggedIn = false;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
                    isLoggedIn = true;
                    break;
                }
            }
        }
        
        return ResponseEntity.ok(isLoggedIn);
    }
}
