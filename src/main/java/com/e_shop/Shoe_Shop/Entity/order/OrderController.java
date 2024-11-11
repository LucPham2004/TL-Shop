package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.DTO.dto.OrderDTO;
import com.e_shop.Shoe_Shop.DTO.request.OrderStatusUpdateRequest;
import com.e_shop.Shoe_Shop.Entity.order.OrderService.OrderRequest;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET
    @GetMapping
    public Page<OrderDTO> getAllOrders(@RequestParam(name = "pageNum") int pageNum){
        return orderService.getAllOrders(pageNum);
    }

    @GetMapping(path = "/customer")
    public Page<OrderDTO> getOrdersByCustomer(
            @RequestParam(name = "customerId") int customerId, 
            @RequestParam(name = "pageNum") int pageNum) {
        return orderService.findByCustomerIdSorted(customerId, pageNum);
    }

    @GetMapping(path = "/sortByStatus")
    public Page<OrderDTO> getSortedOrdersByStatus(@RequestParam(name = "pageNum") int pageNum){
        return orderService.getSortedOrdersByStatus(pageNum);
    }

    @GetMapping("/search")
    public List<OrderDTO> searchOrders(@RequestParam("keyword") String keywword) {
        return orderService.searchOrders(keywword);
    }

    @GetMapping("/search/{id}")
    public List<OrderDTO> searchOrdersByIdContaining(@PathVariable int id) {
        return orderService.searchOrdersByIdContaining(id);
    }

    @GetMapping(path = "/{id}")
    public OrderDTO getOrderById(@PathVariable Integer id){
        return orderService.getOrderById(id);
    }

    // POST
    @PostMapping(path = "/placeOrder")
    public OrderDTO createOrder(@RequestBody OrderRequest orderRequest){
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
