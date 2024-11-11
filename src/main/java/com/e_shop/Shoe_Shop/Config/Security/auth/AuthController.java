package com.e_shop.Shoe_Shop.Config.Security.auth;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.DTO.request.LoginRequest;
import com.e_shop.Shoe_Shop.DTO.request.RegisterRequest;
import com.e_shop.Shoe_Shop.DTO.response.ApiResponse;
import com.e_shop.Shoe_Shop.DTO.response.LoginResponseDTO;

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

    @GetMapping("/status")
    public ApiResponse<String> checkLoginStatus(
        @RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response) {
        String token = authorizationHeader.replace("Bearer ", "");
        return authenticationService.checkLoginStatus(token, response);
    }
}
