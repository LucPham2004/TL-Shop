package com.e_shop.Shoe_Shop;

import java.util.Date;
import java.util.Calendar;
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

				Calendar calendar = Calendar.getInstance();

				calendar.set(Calendar.YEAR, 2024);
				calendar.set(Calendar.MONTH, Calendar.AUGUST);
				calendar.set(Calendar.DAY_OF_MONTH, 1);

				Date dayCreated = calendar.getTime();

				Customer admin = new Customer("admin", encodedPassword, 
					"admin@gmail.com", "099999999", "Ha Noi, Viet Nam", dayCreated, roles);
				customerRepository.save(admin);
			}
		};
	}

}
