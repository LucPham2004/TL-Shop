package com.e_shop.Shoe_Shop;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.role.Role;
import com.e_shop.Shoe_Shop.Entity.role.RoleRepository;

import jakarta.persistence.EntityManager;

@SpringBootApplication
public class ShoeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeShopApplication.class, args);
	}

	@Bean
	CommandLineRunner run(	RoleRepository roleRepository, CustomerRepository customerRepository, 
							PasswordEncoder passwordEncoder, EntityManager entityManager) {
		return args -> {
			entityManager.clear();
			Role adminRole = roleRepository.findByAuthority("ADMIN").orElse(null);
			Role userRole = roleRepository.findByAuthority("USER").orElse(null);
	
			if (adminRole == null) {
				adminRole = roleRepository.save(new Role("ADMIN"));
			}
			if (userRole == null) {
				userRole = roleRepository.save(new Role("USER"));
			}
	
			if (!customerRepository.existsByName("admin")) {
				Set<Role> roles = new HashSet<>();
				roles.add(adminRole);
				roles.add(userRole);
		
				String rawPassword = "adminPassword";
				String encodedPassword = passwordEncoder.encode(rawPassword);

				Customer admin = new Customer("admin", encodedPassword, 
					"phamtienluc0601@gmail.com", "0383132114", "Ha Noi, Viet Nam", roles);
				customerRepository.save(admin);
			}
		};
	}

}
