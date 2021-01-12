package com.example.mad_project_v01;

public class Customer {

    private String title;
    private String fname;
    private String lname;
    private String address;
    private String city;
    private String mobile;
    private String password;

    public Customer() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(String title, String fname, String lname, String address, String city, String mobile, String password) {
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.city = city;
        this.mobile = mobile;
        this.password = password;
    }
}
