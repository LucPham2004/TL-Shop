package com.e_shop.Shoe_Shop.Config.Security.auth;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.DTO.request.LoginRequest;
import com.e_shop.Shoe_Shop.DTO.request.RegisterRequest;
import com.e_shop.Shoe_Shop.DTO.response.ApiResponse;
import com.e_shop.Shoe_Shop.DTO.response.LoginResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authenticationService;
    
    public AuthController(@Lazy AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.logout(request, response);
    }

    // @GetMapping("/status")
    // public ResponseEntity<Boolean> checkLoginStatus(HttpServletRequest request) {
    //     return authenticationService.checkLoginStatus(request);
    // }
}
