package com.e_shop.Shoe_Shop.DTO.response;

import com.e_shop.Shoe_Shop.DTO.dto.CustomerDTO;

public class LoginResponseDTO {
    private CustomerDTO user;
    private String jwt;

    public LoginResponseDTO(CustomerDTO user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }
    public LoginResponseDTO() {
    }

    public CustomerDTO getUser() {
        return user;
    }
    public void setUser(CustomerDTO user) {
        this.user = user;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
