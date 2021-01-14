package com.example.mad_project_v01;

public class CalTotal {

    private long Total = 0;

    public CalTotal() {
    }

    public CalTotal(long total) {
        Total = Total + total;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = Total + total;
    }
}
