package com.e_shop.Shoe_Shop.Security.auth;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;

public class LoginResponseDTO {
    private Customer user;
    private String jwt;

    public LoginResponseDTO(Customer user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }
    public LoginResponseDTO() {
    }

    public Customer getUser() {
        return user;
    }
    public void setUser(Customer user) {
        this.user = user;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
