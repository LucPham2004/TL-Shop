package com.e_shop.Shoe_Shop.dto.dto;

import java.util.Date;
import java.util.Set;

import com.e_shop.Shoe_Shop.entity.Order;
import com.e_shop.Shoe_Shop.entity.Review;
import com.e_shop.Shoe_Shop.entity.Role;


public class CustomerDTO {
    private Integer id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private Date dayCreated;
    private Set<Role> authorities;
    private Set<Order> order;
    private Set<Review> review;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public CustomerDTO(Integer id, String email, String name, String phone, String address, Date dayCreated, Set<Role> authorities,
            Set<Order> order, Set<Review> review, boolean isAccountNonExpired, boolean isAccountNonLocked,
            boolean isCredentialsNonExpired, boolean isEnabled) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.dayCreated = dayCreated;
        this.authorities = authorities;
        this.order = order;
        this.review = review;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public CustomerDTO() {
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDayCreated() {
        return dayCreated;
    }

    public void setDayCreated(Date dayCreated) {
        this.dayCreated = dayCreated;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Set<Review> getReview() {
        return review;
    }

    public void setReview(Set<Review> review) {
        this.review = review;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}