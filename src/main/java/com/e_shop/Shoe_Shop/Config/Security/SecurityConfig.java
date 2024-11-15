package com.e_shop.Shoe_Shop.Config.Security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.e_shop.Shoe_Shop.utils.SecurityUtils;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${me_social.jwt.base64-secret}")
    private String jwtKey;

    public SecurityConfig() {
    }

    public SecurityConfig(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    @Bean
    public AuthenticationManager authManager(@Lazy UserDetailsCustom userDetailsCustom) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsCustom);
        return new ProviderManager(daoProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/customers/**").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN");
                    
                    auth.requestMatchers("/products/**").hasRole("ADMIN");
                    auth.requestMatchers("/brand/**").hasRole("ADMIN");
                    auth.requestMatchers("/category/**").hasRole("ADMIN");
                    
                    auth.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/admin/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/brand/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/category/**").permitAll();

                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer((oauth2) -> oauth2
                    .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("permission");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey())
                .macAlgorithm(SecurityUtils.JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (JwtException e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtils.JWT_ALGORITHM.getName());
    }
}
