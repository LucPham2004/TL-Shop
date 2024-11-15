package com.e_shop.Shoe_Shop.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.dto.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.dto.request.LoginRequest;
import com.e_shop.Shoe_Shop.dto.request.RegisterRequest;
import com.e_shop.Shoe_Shop.dto.response.ApiResponse;
import com.e_shop.Shoe_Shop.dto.response.LoginResponse;
import com.e_shop.Shoe_Shop.entity.Customer;
import com.e_shop.Shoe_Shop.entity.Role;
import com.e_shop.Shoe_Shop.exception.AppException;
import com.e_shop.Shoe_Shop.exception.ErrorCode;
import com.e_shop.Shoe_Shop.repository.RoleRepository;
import com.e_shop.Shoe_Shop.service.CustomerService;
import com.e_shop.Shoe_Shop.utils.SecurityUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, CustomerService customerService,
            SecurityUtils securityUtils, RoleRepository roleRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.customerService = customerService;
        this.securityUtils = securityUtils;
        this.roleRepository = roleRepository;
    }

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CustomerService customerService;
    private final SecurityUtils securityUtils;
    private final RoleRepository roleRepository;

     @PostMapping("/login")
     public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {

          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword());

          System.out.println("Attempting to authenticate user: " + loginRequest.getEmail());

          // Authenticate the user
          Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
          SecurityContextHolder.getContext().setAuthentication(authentication);

          System.out.println("authentication principal: {}" + authentication.getPrincipal());

          // Prepare the login response
          LoginResponse loginResponse = new LoginResponse();
          Customer currentUserDB = customerService.handleGetCustomerByEmail(loginRequest.getEmail());

          if (currentUserDB != null) {
               LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                         currentUserDB.getId(),
                         currentUserDB.getEmail(),
                         currentUserDB.getAuthorities());
               loginResponse.setUser(userLogin);
          }

          // Generate tokens
          String access_token = securityUtils.createAccessToken(authentication.getName(), loginResponse);
          loginResponse.setJwt(access_token);

          String refresh_token = securityUtils.createRefreshToken(loginRequest.getEmail(), loginResponse);

          // Update refresh token for the user in the database
          customerService.updateUserToken(refresh_token, loginRequest.getEmail());

          // Set refresh token as an HTTP-only cookie
          ResponseCookie resCookie = ResponseCookie.from("refresh_token", refresh_token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(securityUtils.refreshTokenExpiration)
                    .build();

          // Create the ApiResponse
          ApiResponse<LoginResponse> apiResponse = new ApiResponse<>();
            apiResponse.setCode(201);
            apiResponse.setMessage("Create customer successfully!");
            apiResponse.setResult(loginResponse);

          // Build the response with the body and headers
          return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, resCookie.toString()) // Set the cookie in the response header
                    .body(apiResponse); // Return the body with the API response
     }

     @GetMapping("/account")
     public ApiResponse<LoginResponse.UserGetAccount> getAccount() {
          String login = SecurityUtils.getCurrentUserLogin().isPresent()
                    ? SecurityUtils.getCurrentUserLogin().get()
                    : "";

          Customer currentUserDB = this.customerService.handleGetCustomerByEmail(login);
          LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin();
          LoginResponse.UserGetAccount userGetAccount = new LoginResponse.UserGetAccount();
          if (currentUserDB != null) {
               userLogin.setId(currentUserDB.getId());
               userLogin.setEmail(currentUserDB.getEmail());
               userLogin.setAuthorities(currentUserDB.getAuthorities());

               userGetAccount.setUser(userLogin);
          }

        ApiResponse<LoginResponse.UserGetAccount> apiResponse = new ApiResponse<>();
        apiResponse.setCode(201);
        apiResponse.setMessage("Create customer successfully!");
        apiResponse.setResult(userGetAccount);

        return apiResponse;
     }

     @GetMapping("/refresh")
     public ResponseEntity<ApiResponse<LoginResponse>> getRefreshToken(
               @CookieValue(name = "refresh_token", defaultValue = "blabla") String refresh_token) {
          if (refresh_token.equals("blabla")) {
               throw new AppException(ErrorCode.NO_REFRESH_TOKEN);
          }
          // check valid
          Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refresh_token);
          String emailUsernamePhone = decodedToken.getSubject();

          // check user by token + email
          Customer currentUser = this.customerService.getUserByRefreshTokenAndEmailOrUsernameOrPhone(refresh_token,
                    emailUsernamePhone);

          // issue new token / set refresh token as cookies
          LoginResponse res = new LoginResponse();

          LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                    currentUser.getId(),
                    currentUser.getEmail(),
                    currentUser.getAuthorities());
          res.setUser(userLogin);

          String access_token = this.securityUtils.createAccessToken(emailUsernamePhone, res);

          res.setJwt(access_token);

          // create refresh token
          String new_refresh_token = this.securityUtils.createRefreshToken(emailUsernamePhone, res);

          // update refreshToken for user
          this.customerService.updateUserToken(new_refresh_token, emailUsernamePhone);

          // set cookies
          ResponseCookie resCookie = ResponseCookie
                    .from("refresh_token", new_refresh_token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(securityUtils.refreshTokenExpiration)
                    .build();

          ApiResponse<LoginResponse> apiResponse = new ApiResponse<>();

          apiResponse.setCode(1000);
          apiResponse.setMessage("get refresh token successfully!");
          apiResponse.setResult(res);

          return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                    .body(apiResponse);
     }

     @PostMapping("/logout")
     public ResponseEntity<ApiResponse<Void>> logout() {
          String emailUsernamePhone = SecurityUtils.getCurrentUserLogin().isPresent()
                    ? SecurityUtils.getCurrentUserLogin().get()
                    : "";

          if (emailUsernamePhone.equals("")) {
               throw new AppException(ErrorCode.INVALID_ACCESS_TOKEN);
          }

          // update refresh token = null
          this.customerService.updateUserToken(null, emailUsernamePhone);

          ApiResponse<Void> apiResponse = new ApiResponse<>();
          apiResponse.setCode(1000);
          apiResponse.setMessage("Log out successfully!");

          // remove fresh token from cookie`
          @SuppressWarnings("null")
        ResponseCookie deleteSpringCookie = ResponseCookie
                    .from("refresh_token", null)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(0)
                    .build();

          return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                    .body(apiResponse);

     }

     @PostMapping("/register")
     public ApiResponse<CustomerDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {

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

        ApiResponse<CustomerDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(201);
        apiResponse.setMessage("Create customer successfully!");
        apiResponse.setResult(customerService.createCustomer(newCustomer));

        return apiResponse;
     }

}
