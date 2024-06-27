package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.order.OrderService.OrderRequest;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrdersByCustomer(Customer customer){
        return orderService.getOrdersByCustomer(customer);
    }

    @GetMapping(path = "/{id}")
    public Order getOrderByIdAndCustomer(@PathVariable Integer id, Customer customer){
        return orderService.getOrderByIdAndCustomer(id, customer);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }

    @DeleteMapping
    public void deleteOrder(Integer id, Customer customer){
        orderService.DeleteOrder(id, customer);
    }
}
