package com.e_shop.Shoe_Shop.dto.request;

public class OrderStatusUpdateRequest {
    private int id;
    private String status;

    public OrderStatusUpdateRequest(int id, String status) {
        this.id = id;
        this.status = status;
    }
    public OrderStatusUpdateRequest() {
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
