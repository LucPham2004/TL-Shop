package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.Entity.order.OrderService.OrderRequest;
import com.e_shop.Shoe_Shop.DTO.request.OrderStatusUpdateRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable int customerId){
        return orderService.getOrdersByCustomer(customerId);
    }

    @GetMapping(path = "/sortByStatus")
    public List<Order> getSortedOrdersByStatus(){
        return orderService.getSortedOrdersByStatus();
    }

    @GetMapping(path = "/{id}")
    public Order getOrderById(@PathVariable Integer id){
        return orderService.getOrderById(id);
    }

    // POST
    @PostMapping(path = "/placeOrder")
    public Order createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public void deleteOrder(@PathVariable int id){
        orderService.DeleteOrder(id);
    }

    // PUT
    @PutMapping
    public void UpdateStatus(@RequestBody OrderStatusUpdateRequest request) {
        if(request.getId() > 0 && request.getStatus() != null) {
            orderService.UpdateStatus(request.getId(), request.getStatus());
        }
    }
}
