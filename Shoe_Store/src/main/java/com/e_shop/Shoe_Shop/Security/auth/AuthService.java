package com.e_shop.Shoe_Shop.Security.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerService;
import com.e_shop.Shoe_Shop.Entity.role.Role;
import com.e_shop.Shoe_Shop.Entity.role.RoleRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class AuthService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (customerRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(400).body("Email taken!");
        }

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        Customer newCustomer = new Customer();
        newCustomer.setName(registerRequest.getName());
        newCustomer.setEmail(registerRequest.getEmail());
        newCustomer.setPhone(registerRequest.getPhone());
        newCustomer.setPassword(registerRequest.getPassword());
        newCustomer.setAuthorities(authorities);

        customerService.createCustomer(newCustomer);

        return ResponseEntity.ok("Sign Up Success!");
    }

    public LoginResponseDTO login(String email, String password, HttpServletResponse response) {
        try {
            System.out.println("Starting login process for email: " + email);
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            String token = tokenService.generateJwt(auth);
            System.out.println("Token generated: " + token);

            Cookie userLoggedIn = new Cookie("userLoggedIn", "true");
            userLoggedIn.setMaxAge(987654321 * 60 * 60); 
            userLoggedIn.setPath("/");

            Customer user = customerRepository.findByEmail(email);
            if (user == null) {
                System.out.println("User not found for email: " + email);
                throw new UsernameNotFoundException("User not found");
            }
            Customer userDTO = user;
            userDTO.setPassword(null);

            response.addCookie(userLoggedIn);
            response.setStatus(HttpServletResponse.SC_OK);
            return new LoginResponseDTO(userDTO, token);
            
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed for email: " + email);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new LoginResponseDTO(null, "");
        }
    }

    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userLoggedIn") || cookie.getName().equals("jwtToken")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return ResponseEntity.ok("Logged out successfully!");
    }

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
