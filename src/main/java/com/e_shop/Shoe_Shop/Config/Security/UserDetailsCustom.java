package com.e_shop.Shoe_Shop.Config.Security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.e_shop.Shoe_Shop.entity.Customer;
import com.e_shop.Shoe_Shop.repository.CustomerRepository;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

     public UserDetailsCustom(CustomerRepository customerRepository) {
          this.customerRepository = customerRepository;
     }

     private final CustomerRepository customerRepository;

     @Override
     public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
          // find user in database
          // = this.userService.handleGetUserByUsername(username);
          Optional<Customer> userOptional = customerRepository.findByEmail(login);

          if (userOptional.isEmpty()) {
               throw new UsernameNotFoundException("User not found with login: " + login);
          }

          // Lấy user từ Optional nếu có
          Customer user = userOptional.get();
          
          return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
     }

}
