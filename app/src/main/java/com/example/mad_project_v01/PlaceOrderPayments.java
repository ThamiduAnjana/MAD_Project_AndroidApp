package com.example.mad_project_v01;

public class PlaceOrderPayments {

    private String card_type;
    private String name;
    private String card_number;
    private String exp_date;
    private String security_code;
    private String order_id;
    private String amount;

    public PlaceOrderPayments() {
    }

    public PlaceOrderPayments(String card_type, String name, String card_number, String exp_date, String security_code, String order_id, String amount) {
        this.card_type = card_type;
        this.name = name;
        this.card_number = card_number;
        this.exp_date = exp_date;
        this.security_code = security_code;
        this.order_id = order_id;
        this.amount = amount;
    }
    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
