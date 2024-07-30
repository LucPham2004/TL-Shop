package com.e_shop.Shoe_Shop.Config.Security.auth;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Config.Security.requestsAndResponses.LoginRequest;
import com.e_shop.Shoe_Shop.Config.Security.requestsAndResponses.LoginResponseDTO;
import com.e_shop.Shoe_Shop.Config.Security.requestsAndResponses.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authenticationService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.logout(request, response);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> checkLoginStatus(HttpServletRequest request) {
        return authenticationService.checkLoginStatus(request);
    }
}
