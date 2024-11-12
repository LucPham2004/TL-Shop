package com.e_shop.Shoe_Shop.entity;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="role_id")
    private Integer id;

    @Column(name = "role_authority")
    private String authority;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @JsonBackReference(value="user_role")
    private Set<Customer> customer;
    
    public Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", authority=" + authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<Customer> customer) {
        this.customer = customer;
    }

}
