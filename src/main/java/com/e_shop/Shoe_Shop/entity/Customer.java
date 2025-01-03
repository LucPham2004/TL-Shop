package com.e_shop.Shoe_Shop.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "password")
    private String password;
    
    @Column(name = "customer_email")
    private String email;
    
    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_address")
    private String address;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    @Column(name = "day_created")
    private Date dayCreated;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable( name = "user_role", 
                joinColumns = @JoinColumn(name = "customer_id"), 
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference(value="user_role")
	private Set<Role> authorities;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "customer_orders")
    private Set<Order> order;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "customer_reviews")
    private Set<Review> review;
    
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;
    
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    // Constructor
    public Customer(String name, String email, String phone, String address, Date dayCreated, Set<Role> authorities) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dayCreated = dayCreated;
        this.authorities = authorities;
    }

    public Customer(String name, String password, String email, String phone, String address, Date dayCreated, Set<Role> authorities) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dayCreated = dayCreated;
        this.authorities = authorities;
    }

    public Customer() {
        super();
        this.authorities = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + 
                ", address=" + address + "day_created=" + dayCreated + ", authorities=" + authorities + 
                ", order=" + order + ", review=" + review + "]";
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
