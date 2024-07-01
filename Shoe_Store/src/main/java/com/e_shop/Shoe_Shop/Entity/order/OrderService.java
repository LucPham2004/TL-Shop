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

	private final Float ShippingCostCurrent = 15000.0f;
    private final Float TaxCurrent = 0.08f;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // GET
	public List<Order> getOrdersByCustomer(int customerId) {
		return orderRepository.findByCustomerId(customerId);
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
        Float TotalPrice = 0.0f;

        newOrder.setDate(new Date());
        newOrder.setCustomer(customerRepository.findById(customerId));
        newOrder.setShippingCost(ShippingCostCurrent);
        newOrder.setStatus("Đã đặt hàng!");
        newOrder.setTax(TaxCurrent);
        

        Set<OrderDetail> newOrderDetails = newOrder.getOrderDetails();

        for (OrderDetailRequest detailRequest : orderDetailRequests) {
            Product product = productRepository.findById(detailRequest.getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setProductName(detailRequest.getProductName());
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setColor(detailRequest.getColor());
            orderDetail.setSize(detailRequest.getSize());
            orderDetail.setCategories(detailRequest.getCategories());
            orderDetail.setUnitPrice(detailRequest.getUnit_price());
            orderDetail.setSubtotal(detailRequest.getQuantity() * detailRequest.getUnit_price());
            TotalPrice += orderDetail.getSubtotal();
            newOrderDetails.add(orderDetail);
        }
        newOrder.setTotal(TotalPrice * (1 + newOrder.getTax()) + newOrder.getShippingCost());

        return orderRepository.save(newOrder);
    }

    // DELETE
    public void DeleteOrder(int id){
		Order order = orderRepository.findById(id);
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
        private String productName;
        private int quantity;
        private Integer size;
        private String color;
        private float unit_price;
        private String categories;

		public String getCategories() {
            return categories;
        }
        public void setCategories(String categories) {
            this.categories = categories;
        }
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
		public float getUnit_price() {
			return unit_price;
		}
		public void setUnit_price(float unit_price) {
			this.unit_price = unit_price;
		}
        public String getProductName() {
            return productName;
        }
        public void setProductName(String productName) {
            this.productName = productName;
        }
        public Integer getSize() {
            return size;
        }
        public void setSize(Integer size) {
            this.size = size;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }

    }

}
