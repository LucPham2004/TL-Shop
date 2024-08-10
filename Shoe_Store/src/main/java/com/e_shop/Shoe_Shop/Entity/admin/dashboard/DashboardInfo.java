package com.e_shop.Shoe_Shop.Entity.admin.dashboard;

import org.springframework.stereotype.Component;

import com.e_shop.Shoe_Shop.DTO.dto.CustomerDTO;
import com.e_shop.Shoe_Shop.DTO.dto.OrderDTO;
import com.e_shop.Shoe_Shop.DTO.dto.ProductDTO;

import java.util.List;


@Component
public class DashboardInfo {
    private long totalCategories;
	private long totalBrands;
	private long totalProducts;
	private long totalReviews;
	private long totalCustomers;
	private long totalOrders;
	
	private long enabledCustomersCount;
	private long disabledCustomersCount;
	private long lockedCustomersCount;
	private long nonLockedCustomersCount;
    
	private long enabledProductsCount;
	private long disabledProductsCount;

    private Float totalRevenue;
    
	private long deliveredOrdersCount;
	private long processingOrdersCount;
	private long shippingOrdersCount;
	private long cancelledOrdersCount;
    
	private long reviewedProductsCount;

    private List<CustomerDTO> new_customers;
    private List<OrderDTO> orderList;
    private List<ProductDTO> lowRemainingProducts;

    public DashboardInfo() {
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalBrands() {
        return totalBrands;
    }

    public void setTotalBrands(long totalBrands) {
        this.totalBrands = totalBrands;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getEnabledCustomersCount() {
        return enabledCustomersCount;
    }

    public void setEnabledCustomersCount(long enabledCustomersCount) {
        this.enabledCustomersCount = enabledCustomersCount;
    }

    public long getDisabledCustomersCount() {
        return disabledCustomersCount;
    }

    public void setDisabledCustomersCount(long disabledCustomersCount) {
        this.disabledCustomersCount = disabledCustomersCount;
    }

    public long getLockedCustomersCount() {
        return lockedCustomersCount;
    }

    public void setLockedCustomersCount(long lockedCustomersCount) {
        this.lockedCustomersCount = lockedCustomersCount;
    }

    public long getNonLockedCustomersCount() {
        return nonLockedCustomersCount;
    }

    public void setNonLockedCustomersCount(long nonLockedCustomersCount) {
        this.nonLockedCustomersCount = nonLockedCustomersCount;
    }

    public long getEnabledProductsCount() {
        return enabledProductsCount;
    }

    public void setEnabledProductsCount(long enabledProductsCount) {
        this.enabledProductsCount = enabledProductsCount;
    }

    public long getDisabledProductsCount() {
        return disabledProductsCount;
    }

    public void setDisabledProductsCount(long disabledProductsCount) {
        this.disabledProductsCount = disabledProductsCount;
    }

    public long getDeliveredOrdersCount() {
        return deliveredOrdersCount;
    }

    public void setDeliveredOrdersCount(long deliveredOrdersCount) {
        this.deliveredOrdersCount = deliveredOrdersCount;
    }

    public long getProcessingOrdersCount() {
        return processingOrdersCount;
    }

    public void setProcessingOrdersCount(long processingOrdersCount) {
        this.processingOrdersCount = processingOrdersCount;
    }

    public long getShippingOrdersCount() {
        return shippingOrdersCount;
    }

    public void setShippingOrdersCount(long shippingOrdersCount) {
        this.shippingOrdersCount = shippingOrdersCount;
    }

    public long getCancelledOrdersCount() {
        return cancelledOrdersCount;
    }

    public void setCancelledOrdersCount(long cancelledOrdersCount) {
        this.cancelledOrdersCount = cancelledOrdersCount;
    }

    public long getReviewedProductsCount() {
        return reviewedProductsCount;
    }

    public void setReviewedProductsCount(long reviewedProductsCount) {
        this.reviewedProductsCount = reviewedProductsCount;
    }

    public Float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<CustomerDTO> getNew_customers() {
        return new_customers;
    }

    public void setNew_customers(List<CustomerDTO> new_customers) {
        this.new_customers = new_customers;
    }

    public List<OrderDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDTO> orderList) {
        this.orderList = orderList;
    }
    
	public List<ProductDTO> getLowRemainingProducts() {
        return lowRemainingProducts;
    }

    public void setLowRemainingProducts(List<ProductDTO> lowRemainingProducts) {
        this.lowRemainingProducts = lowRemainingProducts;
    }
}
