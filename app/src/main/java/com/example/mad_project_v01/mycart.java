package com.example.mad_project_v01;

public class mycart {

    private  String p_name;
    private String p_code;
    private Long p_price;
    private int p_qty;

    public mycart() {
    }

    public mycart(String p_name, String p_code, Long p_price, int p_qty) {
        this.p_name = p_name;
        this.p_code = p_code;
        this.p_price = p_price;
        this.p_qty = p_qty;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public Long getP_price() {
        return p_price;
    }

    public void setP_price(Long p_price) {
        this.p_price = p_price;
    }

    public int getP_qty() {
        return p_qty;
    }

    public void setP_qty(int p_qty) {
        this.p_qty = p_qty;
    }
}
