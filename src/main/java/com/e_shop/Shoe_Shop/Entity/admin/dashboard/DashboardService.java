package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.DTO.dto.admin.CustomerSummary;
import com.e_shop.Shoe_Shop.DTO.dto.admin.MainEntitiesSummary;
import com.e_shop.Shoe_Shop.DTO.dto.admin.OrderSummary;
import com.e_shop.Shoe_Shop.DTO.dto.admin.ProductSummary;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerService;
import com.e_shop.Shoe_Shop.Entity.order.OrderRepository;
import com.e_shop.Shoe_Shop.Entity.order.OrderService;
import com.e_shop.Shoe_Shop.Entity.product.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DashboardService {
	private final EntityManager entityManager;
	private final CustomerService customerService;
	private final OrderService orderService;
	private final OrderRepository orderRepository;
	private final ProductService productService;

	public DashboardService(EntityManager entityManager, CustomerService customerService, OrderService orderService,
			OrderRepository orderRepository, ProductService productService) {
		this.entityManager = entityManager;
		this.customerService = customerService;
		this.orderService = orderService;
		this.orderRepository = orderRepository;
		this.productService = productService;
	}

	public MainEntitiesSummary entitiesSummary() {
		MainEntitiesSummary summary = new MainEntitiesSummary();
		Query query = entityManager.createQuery("SELECT "
					+ "(SELECT COUNT(DISTINCT p.id) AS totalProducts FROM Product p), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS totalCustomers FROM Customer cu), "
					+ "(SELECT COUNT(DISTINCT o.id) AS totalOrders FROM Order o)");
		
		@SuppressWarnings("unchecked")
        List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		summary.setTotalProducts((Long) arrayCounts[count++]);
		summary.setTotalCustomers((Long) arrayCounts[count++]);
		summary.setTotalOrders((Long) arrayCounts[count++]);
		summary.setTotalRevenue(orderRepository.getTotalRevenue());

		summary.setNew_customers(customerService.newCustomers());
		summary.setOrderList(orderService.getSortedOrdersByStatus(0));
		summary.setLowRemainingProducts(productService.lowRemainingProducts());

		return summary;
	}

	public ProductSummary loadProductSummary() {
		ProductSummary summary = new ProductSummary();
		Query query = entityManager.createQuery("SELECT "
					+ "(SELECT COUNT(DISTINCT p.id) AS totalProducts FROM Product p), "
					+ "(SELECT COUNT(DISTINCT c.id) AS totalCategories FROM Category c), "
					+ "(SELECT COUNT(DISTINCT b.id) AS totalBrands FROM Brand b), "
					+ "(SELECT COUNT(DISTINCT r.id) AS totalReviews FROM Review r), "
					+ "(SELECT COUNT(DISTINCT p.id) AS enabledProducts FROM Product p WHERE p.isEnabled=true), "
					+ "(SELECT COUNT(DISTINCT p.id) AS disabledProducts FROM Product p WHERE p.isEnabled=false), "
					+ "(SELECT COUNT(DISTINCT r.product.id) AS reviewedProducts FROM Review r) ");
		
		@SuppressWarnings("unchecked")
        List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		summary.setTotalProducts((Long) arrayCounts[count++]);
		summary.setTotalCategories((Long) arrayCounts[count++]);
		summary.setTotalBrands((Long) arrayCounts[count++]);
		summary.setTotalReviews((Long) arrayCounts[count++]);
		summary.setEnabledProductsCount((Long) arrayCounts[count++]);
		summary.setDisabledProductsCount((Long) arrayCounts[count++]);
		summary.setReviewedProductsCount((Long) arrayCounts[count++]);

		return summary;
	}

	public OrderSummary loadOrderSummary() {
		OrderSummary summary = new OrderSummary();
		Query query = entityManager.createQuery("SELECT "
					+ "(SELECT COUNT(DISTINCT o.id) AS totalOrders FROM Order o), "
					+ "(SELECT COUNT(DISTINCT o.id) AS processingOrders FROM Order o WHERE o.status = 'Processing'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS shippingOrders FROM Order o WHERE o.status = 'Delivering'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS deliveredOrders FROM Order o WHERE o.status = 'Completed'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS cancelledOrders FROM Order o WHERE o.status = 'Cancelled')");
		
		@SuppressWarnings("unchecked")
        List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		summary.setTotalOrders((Long) arrayCounts[count++]);
		summary.setProcessingOrdersCount((Long) arrayCounts[count++]);
		summary.setShippingOrdersCount((Long) arrayCounts[count++]);
		summary.setDeliveredOrdersCount((Long) arrayCounts[count++]);
		summary.setCancelledOrdersCount((Long) arrayCounts[count++]);

		return summary;
	}

	public CustomerSummary loadCustomerSummary() {
		CustomerSummary summary = new CustomerSummary();
		Query query = entityManager.createQuery("SELECT "
					+ "(SELECT COUNT(DISTINCT cu.id) AS totalCustomers FROM Customer cu), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS enabledCustomers FROM Customer cu WHERE cu.isEnabled=true), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS disabledCustomers FROM Customer cu WHERE cu.isEnabled=false), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS nonLockedCustomers FROM Customer cu WHERE cu.isAccountNonLocked=true), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS lockedCustomers FROM Customer cu WHERE cu.isAccountNonLocked=false)");
		
		@SuppressWarnings("unchecked")
        List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		summary.setTotalCustomers((Long) arrayCounts[count++]);
		summary.setEnabledCustomersCount((Long) arrayCounts[count++]);
		summary.setDisabledCustomersCount((Long) arrayCounts[count++]);
        summary.setNonLockedCustomersCount((Long) arrayCounts[count++]);
		summary.setLockedCustomersCount((Long) arrayCounts[count++]);

		return summary;
	}
}
