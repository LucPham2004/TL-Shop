package com.e_shop.Shoe_Shop.Entity.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.DTO.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.DTO.request.ChangePasswordRequest;
import com.e_shop.Shoe_Shop.DTO.request.CustomerUpdateRequest;

import jakarta.servlet.http.HttpServletResponse;

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

    @GetMapping("/search/{id}")
    public List<CustomerDTO> searchCustomer(@PathVariable int id) {
        return customerService.searchCustomerByIdContaining(id);
    }

    // POST
    @PostMapping("/changePassword")
    public CustomerDTO ChangePassword(ChangePasswordRequest changePasswordRequest, HttpServletResponse response) {
        return customerService.ChangePassword(changePasswordRequest, response);
    }
    
    // DELETE
    @DeleteMapping
    public void deleteCustomerByIdAndEmail(int id, String email)
    {
        customerService.deleteCustomerByIdAndEmail(id, email);
    }

    // UPDATE
    @PutMapping
    public CustomerDTO UpdateCustomer(@RequestBody CustomerUpdateRequest customerUpdateRequest)
    {
        return customerService.UpdateCustomer(customerUpdateRequest.getId(), customerUpdateRequest.getEmail(),
        customerUpdateRequest.getName(), customerUpdateRequest.getPhone(), customerUpdateRequest.getAddress());
    }
}


