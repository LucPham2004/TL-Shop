package com.e_shop.Shoe_Shop.Entity.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Entity.customer.CustomerService.UpdateUserInfoRequest;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET
    @GetMapping
    public List<CustomerDTO> getAllCustomer()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/{id}")
    public CustomerDTO getCustomerById(@PathVariable Integer id)
    {
        return customerService.getCustomerById(id);
    }

    @GetMapping(path = "/Email/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable String email)
    {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomer(@RequestParam("keyword") String keywword) {
        return customerService.searchCustomer(keywword);
    }
    
    // DELETE
    @DeleteMapping
    public void deleteCustomerByIdAndEmail(int id, String email)
    {
        customerService.deleteCustomerByIdAndEmail(id, email);
    }

    // UPDATE
    @PutMapping
    public void UpdateCustomer(@RequestBody UpdateUserInfoRequest UpdateUserInfoRequest)
    {
        customerService.UpdateCustomer(UpdateUserInfoRequest.getId(), UpdateUserInfoRequest.getEmail(),
        UpdateUserInfoRequest.getName(), UpdateUserInfoRequest.getPhone(), UpdateUserInfoRequest.getAddress());
    }
}


