package com.e_shop.Shoe_Shop.Entity.order;

import java.util.*;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.order.detail.OrderDetail;
import com.e_shop.Shoe_Shop.Entity.product.Product;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // GET
	public List<Order> getOrdersByCustomer(Customer customer) {
		return orderRepository.findByCustomer(customer);
	}

    public Order getOrderByIdAndCustomer(Integer id, Customer customer) {
		boolean exist = orderRepository.existsByIdAndCustomer(id, customer);
        if(exist){
            throw new IllegalStateException("Order does not exists!");
        }
		return orderRepository.findByIdAndCustomer(id, customer);
	}

    // POST
    public Order createOrder(Customer customer, List<OrderDetail> orderDetails) {
		Order newOrder = new Order();
        GatherInfo gatherInfo = new GatherInfo();
        gatherInfo.prepareInfo(orderDetails);

		newOrder.setDate(new Date());
		newOrder.setCustomer(customer);
		newOrder.setShippingCost(gatherInfo.getShippingCostTotal());
        newOrder.setStatus("Đã đặt hàng!");
		newOrder.setTax(0.0f);
        newOrder.setTotal(gatherInfo.getTotal() * (1 + newOrder.getTax()));
		
		Set<OrderDetail> newOrderDetails = newOrder.getOrderDetails();
		
		for (OrderDetail cartItem : orderDetails) {
			Product product = cartItem.getProduct();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setShippingCost(cartItem.getShippingCost());
            orderDetail.setUnit_price(cartItem.getUnit_price());
			orderDetail.setSubtotal(cartItem.getSubtotal());
			orderDetail.setShippingCost(cartItem.getShippingCost());
			
			newOrderDetails.add(orderDetail);
		}
		
		return orderRepository.save(newOrder);
	}

    // DELETE
    public void DeleteOrder(int id, Customer customer){
		Order order = orderRepository.findByIdAndCustomer(id, customer);
		if(order == null){
			throw new IllegalStateException("Order does not exists!");
		}
        orderRepository.delete(order);
    }
}
