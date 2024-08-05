package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Entity.order.Order;
import com.e_shop.Shoe_Shop.Entity.order.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DashboardService {
	private final EntityManager entityManager;
    private final DashboardInfo dashboardInfo;
	private final OrderRepository orderRepository;

	public DashboardService(EntityManager entityManager, DashboardInfo dashboardInfo, OrderRepository orderRepository) {
		this.entityManager = entityManager;
		this.dashboardInfo = dashboardInfo;
		this.orderRepository = orderRepository;
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
                    
					+ "(SELECT COUNT(DISTINCT o.id) AS deliveredOrders FROM Order o WHERE o.status = 'Đã giao hàng!'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS processingOrders FROM Order o WHERE o.status = 'Đã đặt hàng!'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS shippingOrders FROM Order o WHERE o.status = 'Đang giao hàng!'), "
					+ "(SELECT COUNT(DISTINCT o.id) AS cancelledOrders FROM Order o WHERE o.status = 'Đã hủy bỏ!'), "

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

        summary.setTotalRevenue(dashboardInfo.getTotalRevenue());
		
		summary.setDeliveredOrdersCount((Long) arrayCounts[count++]);
		summary.setProcessingOrdersCount((Long) arrayCounts[count++]);
		summary.setShippingOrdersCount((Long) arrayCounts[count++]);
		summary.setCancelledOrdersCount((Long) arrayCounts[count++]);
		
		summary.setReviewedProductsCount((Long) arrayCounts[count++]);

		Float revenue = 0.0f;
		List<Order> orders = orderRepository.findAll();
		for(Order order: orders) {
			if(order.getStatus() == "Đã giao hàng!") {
				revenue += order.getTotal();
			}
		}
		summary.setTotalRevenue(revenue);
		
		return summary;
	}
}
