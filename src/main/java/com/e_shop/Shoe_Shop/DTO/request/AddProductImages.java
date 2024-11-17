package com.e_shop.Shoe_Shop.dto.request;

public class AddProductImages {
    private Integer id;
    private String[] urls;
    private String[] publicIds;
    
    public String[] getUrls() {
        return urls;
    }
    public void setUrls(String[] urls) {
        this.urls = urls;
    }
    public String[] getPublicIds() {
        return publicIds;
    }
    public void setPublicIds(String[] publicIds) {
        this.publicIds = publicIds;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
