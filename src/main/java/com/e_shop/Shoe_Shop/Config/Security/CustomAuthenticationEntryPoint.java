package com.e_shop.Shoe_Shop.Config.Security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.e_shop.Shoe_Shop.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

     private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

     private final ObjectMapper mapper;

     public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
          this.mapper = mapper;
     }

     @Override
     public void commence(HttpServletRequest request, HttpServletResponse response,
               AuthenticationException authException) throws IOException, ServletException {
          this.delegate.commence(request, response, authException);
          response.setContentType("application/json;charset=UTF-8");

          ApiResponse<Object> res = new ApiResponse<>();
          res.setCode(HttpStatus.UNAUTHORIZED.value());

          String errorMessage = Optional.ofNullable(authException.getCause()) // NULL
                    .map(Throwable::getMessage)
                    .orElse(authException.getMessage());

          res.setMessage(
                    "Token không hợp lệ (hết hạn, không đúng định dạng, hoặc không truyền JWT ở header)..."
                              + " Specific error: " + errorMessage);

          mapper.writeValue(response.getWriter(), res);
     }
}
