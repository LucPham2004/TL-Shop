package com.e_shop.Shoe_Shop.Entity.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET
    @GetMapping
    public List<Customer> getAllCustomer()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable Integer id)
    {
        return customerService.getCustomerById(id);
    }

    @GetMapping(path = "/Email/{email}")
    public Customer getCustomerByEmail(@PathVariable String email)
    {
        return customerService.getCustomerByEmail(email);
    }

    // POST
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }
    
    // DELETE
    @DeleteMapping
    public void deleteCustomerByIdAndEmail(int id, String email)
    {
        customerService.deleteCustomerByIdAndEmail(id, email);
    }

    // UPDATE
    @PutMapping
    public void UpdateCustomer(@RequestBody UpdateRequest request)
    {
        customerService.UpdateCustomer(request.getId(), request.getEmail(), 
        request.getName(), request.getPhone(), request.getAddress());
    }
}


