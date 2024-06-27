package com.e_shop.Shoe_Shop.Entity.order;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.Customer;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.order.detail.OrderDetail;
import com.e_shop.Shoe_Shop.Entity.product.Product;
import com.e_shop.Shoe_Shop.Entity.product.ProductRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;

	private final Float ShippingCostMinimum = 15000.0f;

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
    public Order createOrder(OrderRequest orderRequest) {
        int customerId = orderRequest.getCustomerId();
        List<OrderDetailRequest> orderDetailRequests = orderRequest.getOrderDetails();

        Order newOrder = new Order();
        GatherInfo gatherInfo = new GatherInfo();

        newOrder.setDate(new Date());
        newOrder.setCustomer(customerRepository.findById(customerId));
        newOrder.setShippingCost(gatherInfo.getShippingCostTotal());
        newOrder.setStatus("Đã đặt hàng!");
        newOrder.setTax(0.0f);
        newOrder.setTotal(gatherInfo.getTotal() * (1 + newOrder.getTax()));

        Set<OrderDetail> newOrderDetails = newOrder.getOrderDetails();

        for (OrderDetailRequest detailRequest : orderDetailRequests) {
            Product product = productRepository.findById(detailRequest.getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setShippingCost(ShippingCostMinimum);
            orderDetail.setUnit_price(detailRequest.getUnit_price());
            orderDetail.setSubtotal(detailRequest.getQuantity() * detailRequest.getUnit_price());

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
	

	// Create Order Request
    static class OrderRequest {
        private int customerId;
        private List<OrderDetailRequest> orderDetails;

		public int getCustomerId() {
			return customerId;
		}
		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}
		public List<OrderDetailRequest> getOrderDetails() {
			return orderDetails;
		}
		public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
			this.orderDetails = orderDetails;
		}

    }

    static class OrderDetailRequest {
        private int productId;
        private int quantity;
        private float shippingCost;
        private float unit_price;
        private float subtotal;

		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public float getShippingCost() {
			return shippingCost;
		}
		public void setShippingCost(float shippingCost) {
			this.shippingCost = shippingCost;
		}
		public float getUnit_price() {
			return unit_price;
		}
		public void setUnit_price(float unit_price) {
			this.unit_price = unit_price;
		}
		public float getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(float subtotal) {
			this.subtotal = subtotal;
		}

    }

}
