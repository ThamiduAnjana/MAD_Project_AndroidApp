package com.example.mad_project_v01;

public class CommentsAndComplaints {

    private String select_type;

    public CommentsAndComplaints(String select_type, String cus_mname, String message) {
        this.select_type = select_type;
        this.cus_mname = cus_mname;
        this.message = message;
    }

    private String cus_mname;
    private String message;

    public String getSelect_type() {
        return select_type;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
    }

    public String getCus_name() {
        return cus_mname;
    }

    public void setCus_name(String cus_mname) {
        this.cus_mname = cus_mname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentsAndComplaints() {
    }
}
