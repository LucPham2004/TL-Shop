package com.e_shop.Shoe_Shop.Entity.admin.dto;

public class OrderSummary {
	private long totalOrders;
	private long deliveredOrdersCount;
	private long processingOrdersCount;
	private long shippingOrdersCount;
	private long cancelledOrdersCount;
    
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

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
