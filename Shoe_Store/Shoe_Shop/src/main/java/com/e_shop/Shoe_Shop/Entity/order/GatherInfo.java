package com.e_shop.Shoe_Shop.Entity.order;

import java.util.List;

import com.e_shop.Shoe_Shop.Entity.order.detail.OrderDetail;

public class GatherInfo {
    
    private float productTotal;
	private float shippingCostTotal;
    private float Total;

    public GatherInfo prepareInfo(List<OrderDetail> orderDetail) {
		GatherInfo GatherInfo = new GatherInfo();
		
		float productTotal = calculateProductTotal(orderDetail);
		float shippingCostTotal = calculateShippingCost();
		float Total = productTotal + shippingCostTotal;
		
		GatherInfo.setProductTotal(productTotal);
		GatherInfo.setShippingCostTotal(shippingCostTotal);
		GatherInfo.setTotal(Total);
		
		return GatherInfo;
	}

	private float calculateShippingCost() {
		float shippingCostTotal = 15000.0f;
		
		return shippingCostTotal;
	}

	private float calculateProductTotal(List<OrderDetail> orderDetail) {
		float total = 0.0f;
		
		for (OrderDetail item : orderDetail) {
			total += item.getSubtotal();
		}
		
		return total;
	}

    public GatherInfo() {
    }

    public GatherInfo(float productTotal, float shippingCostTotal, float total) {
        this.productTotal = productTotal;
        this.shippingCostTotal = shippingCostTotal;
        Total = total;
    }

    @Override
    public String toString() {
        return "GatherInfo [productTotal=" + productTotal + ", shippingCostTotal=" + shippingCostTotal + ", Total="
                + Total + "]";
    }

    public float getProductTotal() {
        return productTotal;
    }
    public void setProductTotal(float productTotal) {
        this.productTotal = productTotal;
    }
    public float getShippingCostTotal() {
        return shippingCostTotal;
    }
    public void setShippingCostTotal(float shippingCostTotal) {
        this.shippingCostTotal = shippingCostTotal;
    }
    public float getTotal() {
        return Total;
    }
    public void setTotal(float total) {
        Total = total;
    }
}
