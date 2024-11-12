package com.e_shop.Shoe_Shop.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.dto.request.LoginRequest;
import com.e_shop.Shoe_Shop.dto.request.RegisterRequest;
import com.e_shop.Shoe_Shop.dto.response.ApiResponse;
import com.e_shop.Shoe_Shop.dto.response.LoginResponseDTO;
import com.e_shop.Shoe_Shop.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authenticationService;
    
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
    // public ApiResponse<String> checkLoginStatus(
    //     @RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response) {
    //     String token = authorizationHeader.replace("Bearer ", "");
    //     return authenticationService.checkLoginStatus(token, response);
    // }
}
