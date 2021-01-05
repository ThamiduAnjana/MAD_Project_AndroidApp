package com.example.mad_project_v01;

public class mycart {

    private Long p_price;
    private int p_qty;

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

    public mycart(Long p_price, int p_qty) {
        this.p_price = p_price;
        this.p_qty = p_qty;
    }

    public mycart() {
    }
//    private long p_discount;
//    private long p_total;
}
