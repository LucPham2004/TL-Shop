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

@SpringBootApplication
public class ShoeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeShopApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			Customer admin  = new Customer("admin", null, null, null, roles);
			customerRepository.save(admin);
		};
	}

}
