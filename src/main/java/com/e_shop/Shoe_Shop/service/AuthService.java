package com.e_shop.Shoe_Shop.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_shop.Shoe_Shop.dto.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.dto.request.RegisterRequest;
import com.e_shop.Shoe_Shop.dto.response.ApiResponse;
import com.e_shop.Shoe_Shop.dto.response.LoginResponseDTO;
import com.e_shop.Shoe_Shop.entity.Customer;
import com.e_shop.Shoe_Shop.entity.Role;
import com.e_shop.Shoe_Shop.repository.CustomerRepository;
import com.e_shop.Shoe_Shop.repository.RoleRepository;
import com.nimbusds.jose.JOSEException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class AuthService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(@Lazy CustomerService customerService, CustomerRepository customerRepository,
            RoleRepository roleRepository, @Lazy AuthenticationManager authenticationManager, TokenService tokenService) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ApiResponse<String> register(RegisterRequest registerRequest) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        if (customerRepository.existsByEmail(registerRequest.getEmail())) {
            apiResponse.setCode(400);
            apiResponse.setMessage("Email taken!");
            return apiResponse;
        }

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        Customer newCustomer = new Customer();
        newCustomer.setName(registerRequest.getName());
        newCustomer.setEmail(registerRequest.getEmail());
        newCustomer.setPhone(registerRequest.getPhone());
        newCustomer.setPassword(registerRequest.getPassword());
        newCustomer.setDayCreated(new Date());
        newCustomer.setAuthorities(authorities);

        customerService.createCustomer(newCustomer);

        apiResponse.setCode(201);
        apiResponse.setMessage("Create customer successfully!");
        apiResponse.setResult("Create customer successfully!");

        return apiResponse;
    }

    public ApiResponse<LoginResponseDTO> login(String email, String password, HttpServletResponse response) {
        try {
            System.out.println("Starting login process for email: " + email);
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println("auth: " + auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
    
            String token = tokenService.generateJwt(auth);
    
            Customer user = customerRepository.findByEmail(email);
            if (user == null) {
                System.out.println("User not found for email: " + email);
                throw new UsernameNotFoundException("User not found");
            }
            CustomerDTO userDTO = customerService.convertToDTO(user);
            response.setStatus(HttpServletResponse.SC_OK);
            
            LoginResponseDTO loginResponse = new LoginResponseDTO(userDTO, token);
            return new ApiResponse<>(200, "Login successful", loginResponse);
    
        } catch (AuthenticationException | JOSEException e) {
            System.out.println("Authentication failed for email: " + email);
            e.toString();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ApiResponse<>(401, "Authentication failed", null);
        }
    }

    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        
        return ResponseEntity.ok("Logged out successfully!");
    }

    // Check if token is verified and not expired
    // public ApiResponse<String> checkLoginStatus(String token, HttpServletResponse response) {
    //     try {
    //         SignedJWT verifyToken = tokenService.verifyToken(token);
            
    //         ApiResponse<String> apiResponse = new ApiResponse<>();

    //         apiResponse.setCode(200);
    //         apiResponse.setMessage("Token verified!");
    //         apiResponse.setResult(verifyToken.toString());
            
    //         return apiResponse;

    //     } catch (JOSEException | ParseException e) {
    //         e.printStackTrace();
    //         ApiResponse<String> apiResponse = new ApiResponse<>();
            
    //         if (e instanceof JOSEException) {
    //             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //             apiResponse.setCode(401);
    //             apiResponse.setMessage("Token verification failed: " + e.getMessage());
    //         } else if (e instanceof ParseException) {
    //             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    //             apiResponse.setCode(400);
    //             apiResponse.setMessage("Token parsing failed: " + e.getMessage());
    //         }
            
    //         apiResponse.setResult("");
    //         return apiResponse;
    //     } 
    // }
}
