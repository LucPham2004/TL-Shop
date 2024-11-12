package com.e_shop.Shoe_Shop.dto.response;

public class FileNameResponse {
    private String banner;
    private String poster;
    private String sample;
    
    public FileNameResponse(String banner, String poster, String sample) {
        this.banner = banner;
        this.poster = poster;
        this.sample = sample;
    }
    public FileNameResponse() {
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public String getSample() {
        return sample;
    }
    public void setSample(String sample) {
        this.sample = sample;
    }
}