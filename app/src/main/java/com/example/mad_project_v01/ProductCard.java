package com.example.mad_project_v01;

public class ProductCard {

    private String p_des;
    private String p_image;
    private String p_name;
    private Long p_price;

    public ProductCard() {
    }

    public ProductCard(String p_des, String p_image, String p_name, Long p_price) {
        this.p_des = p_des;
        this.p_image = p_image;
        this.p_name = p_name;
        this.p_price = p_price;
    }

    public String getP_des() {
        return p_des;
    }

    public void setP_des(String p_des) {
        this.p_des = p_des;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public Long getP_price() {
        return p_price;
    }

    public void setP_price(Long p_price) {
        this.p_price = p_price;
    }
}
