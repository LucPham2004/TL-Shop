package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.DTO.dto.OrderDTO;
import com.e_shop.Shoe_Shop.Entity.customer.CustomerService;
import com.e_shop.Shoe_Shop.Entity.order.OrderService;
import com.e_shop.Shoe_Shop.Entity.product.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DashboardService {
	private final EntityManager entityManager;
	private final CustomerService customerService;
	private final OrderService orderService;
	private final ProductService productService;

	public DashboardService(EntityManager entityManager,  CustomerService customerService, 
							OrderService orderService, ProductService productService) {
		this.entityManager = entityManager;
		this.customerService = customerService;
		this.orderService = orderService;
		this.productService = productService;
	}

	public DashboardInfo loadSummary() {
		DashboardInfo summary = new DashboardInfo();
		Query query = entityManager.createQuery("SELECT "
					+ "(SELECT COUNT(DISTINCT c.id) AS totalCategories FROM Category c), "
					+ "(SELECT COUNT(DISTINCT b.id) AS totalBrands FROM Brand b), "
					+ "(SELECT COUNT(DISTINCT p.id) AS totalProducts FROM Product p), "
					+ "(SELECT COUNT(DISTINCT r.id) AS totalReviews FROM Review r), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS totalCustomers FROM Customer cu), "
					+ "(SELECT COUNT(DISTINCT o.id) AS totalOrders FROM Order o), "

					+ "(SELECT COUNT(DISTINCT cu.id) AS enabledCustomers FROM Customer cu WHERE cu.isEnabled=true), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS disabledCustomers FROM Customer cu WHERE cu.isEnabled=false), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS nonLockedCustomers FROM Customer cu WHERE cu.isAccountNonLocked=true), "
					+ "(SELECT COUNT(DISTINCT cu.id) AS lockedCustomers FROM Customer cu WHERE cu.isAccountNonLocked=false), "

					+ "(SELECT COUNT(DISTINCT p.id) AS enabledProducts FROM Product p WHERE p.isEnabled=true), "
					+ "(SELECT COUNT(DISTINCT p.id) AS disabledProducts FROM Product p WHERE p.isEnabled=false), "
                    
					+ "(SELECT COUNT(DISTINCT o.id) AS deliveredOrders FROM Order o WHERE o.status = 'Completed'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS processingOrders FROM Order o WHERE o.status = 'Processing'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS shippingOrders FROM Order o WHERE o.status = 'Delivering'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS cancelledOrders FROM Order o WHERE o.status = 'Cancelled'), "

					+ "(SELECT COUNT(DISTINCT r.product.id) AS reviewedProducts FROM Review r) ");
		
		@SuppressWarnings("unchecked")
        List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		summary.setTotalCategories((Long) arrayCounts[count++]);
		summary.setTotalBrands((Long) arrayCounts[count++]);
		summary.setTotalProducts((Long) arrayCounts[count++]);
		summary.setTotalReviews((Long) arrayCounts[count++]);
		summary.setTotalCustomers((Long) arrayCounts[count++]);
		summary.setTotalOrders((Long) arrayCounts[count++]);

		summary.setEnabledCustomersCount((Long) arrayCounts[count++]);
		summary.setDisabledCustomersCount((Long) arrayCounts[count++]);
        summary.setNonLockedCustomersCount((Long) arrayCounts[count++]);
		summary.setLockedCustomersCount((Long) arrayCounts[count++]);
		
		summary.setEnabledProductsCount((Long) arrayCounts[count++]);
		summary.setDisabledProductsCount((Long) arrayCounts[count++]);
		
		summary.setDeliveredOrdersCount((Long) arrayCounts[count++]);
		summary.setProcessingOrdersCount((Long) arrayCounts[count++]);
		summary.setShippingOrdersCount((Long) arrayCounts[count++]);
		summary.setCancelledOrdersCount((Long) arrayCounts[count++]);
		
		summary.setReviewedProductsCount((Long) arrayCounts[count++]);

		Float revenue = 0.0f;
		List<OrderDTO> orders = orderService.getSortedOrdersByStatus();
		for(OrderDTO order: orders) {
			if(order.getStatus().equals("Completed")) {
				revenue += order.getTotal();
			}
		}
		summary.setTotalRevenue(revenue);

		summary.setNew_customers(customerService.newCustomers());
		summary.setOrderList(orders);
		summary.setLowRemainingProducts(productService.lowRemainingProducts());

		return summary;
	}
}
