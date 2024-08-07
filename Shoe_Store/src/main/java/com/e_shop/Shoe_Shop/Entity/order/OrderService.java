package com.e_shop.Shoe_Shop.Entity.order;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.customer.CustomerRepository;
import com.e_shop.Shoe_Shop.Entity.order.detail.OrderDetail;
import com.e_shop.Shoe_Shop.Entity.product.Product;
import com.e_shop.Shoe_Shop.Entity.product.ProductRepository;
import com.e_shop.Shoe_Shop.Entity.product.detail.ProductDetail;
import com.e_shop.Shoe_Shop.Entity.product.detail.ProductDetailRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

	private final Float ShippingCostCurrent = 15000.0f;
    private final Float TaxCurrent = 0.08f;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
            ProductRepository productRepository, ProductDetailRepository productDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    // GET
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

	public List<Order> getOrdersByCustomer(int customerId) {
		List<Order> orders = orderRepository.findByCustomerId(customerId);

        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order2.getDate().compareTo(order1.getDate());
            }
        });

        return orders;
	}

    public Order getOrderById(Integer id) {
		boolean exist = orderRepository.existsById(id);
        if(exist){
            throw new IllegalStateException("Order does not exists!");
        }
		return orderRepository.findById(id).get();
	}

    public List<Order> getSortedOrdersByStatus() {
        List<Order> orders = orderRepository.findAll();

        Comparator<Order> statusComparator = (o1, o2) -> {
            List<String> statusOrder = List.of("Processing", "Delivering", "Completed", "Cancelled");
            return Integer.compare(statusOrder.indexOf(o1.getStatus()), statusOrder.indexOf(o2.getStatus()));
        };
        
        return orders.stream()
                    .sorted(statusComparator)
                    .collect(Collectors.toList());
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
        newOrder.setStatus("Processing");
        newOrder.setTax(TaxCurrent);
        

        Set<OrderDetail> newOrderDetails = newOrder.getOrderDetails();

        for (OrderDetailRequest detailRequest : orderDetailRequests) {
            Product product = productRepository.findById(detailRequest.getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setProductName(detailRequest.getProductName());
            orderDetail.setProductImage(detailRequest.getProductImage());
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setColor(detailRequest.getColor());
            orderDetail.setSize(detailRequest.getSize());
            orderDetail.setCategories(detailRequest.getCategories());
            orderDetail.setUnitPrice(detailRequest.getUnit_price());
            orderDetail.setSubtotal(detailRequest.getQuantity() * detailRequest.getUnit_price());
            TotalPrice += orderDetail.getSubtotal();

            newOrderDetails.add(orderDetail);
            Set<ProductDetail> productDetail = product.getDetails();
            for(ProductDetail detail: productDetail) {
                if(detail.getColor().equals(orderDetail.getColor()) && detail.getSize().equals(orderDetail.getSize())) {
                    detail.setQuantitySold(detail.getQuantitySold() + orderDetail.getQuantity());
                    detail.setQuantity(detail.getQuantity() - orderDetail.getQuantity());
                    productDetailRepository.save(detail);
                }
            }
            product.setProductQuantitySold(product.getProductQuantitySold());
            product.setProductQuantity(product.getProductQuantity());
            productRepository.save(product);
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

    // PUT
    public void UpdateStatus(int id, String status) {
        Order order = orderRepository.findById(id);
        if(!order.getStatus().equals(status)) {
            order.setStatus(status);
            orderRepository.save(order);
        }
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
        private String productImage;
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
        public String getProductImage() {
            return productImage;
        }
        public void setProductImage(String productImage) {
            this.productImage = productImage;
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
