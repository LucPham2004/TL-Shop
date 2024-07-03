package com.e_shop.Shoe_Shop.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // @Bean
    //  public UserDetailsService userDetailsService() {
    //      UserDetails user = User.withDefaultPasswordEncoder()
    //          .username("user")
    //          .password("password")
    //          .roles("USER")
    //          .build();
    //      UserDetails admin = User.withDefaultPasswordEncoder()
    //          .username("admin")
    //          .password("password")
    //          .roles("ADMIN", "USER")
    //          .build();
    //      return new InMemoryUserDetailsManager(user, admin);
    // }
}
