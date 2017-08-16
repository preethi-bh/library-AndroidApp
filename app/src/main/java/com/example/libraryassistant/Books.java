package com.example.libraryassistant;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Atreyasa on 7/6/2017.
 */

public class Books {

    @SerializedName("username")
    String username;
    @SerializedName("bname")
    String bname;
    @SerializedName("edition")
    String edition;
    @SerializedName("subject")
    String subject;
    @SerializedName("mobileno")
    int mobileno;
    @SerializedName("bookid")
    int bookid;
    @SerializedName("renew_date")
    Date renew_date;
    @SerializedName("issue_date")
    Date issue_date;
    @SerializedName("status")
    String status;
    @SerializedName("rollno")
    String rollno;
    @SerializedName("author")
    String author;

    public Books(String username, String bname, String edition, String subject, int mobileno, int bookid, Date renew_date, Date issue_date, String status, String rollno) {
        this.username = username;
        this.bname = bname;
        this.edition = edition;
        this.subject = subject;
        this.mobileno = mobileno;
        this.bookid = bookid;
        this.renew_date = renew_date;
        this.issue_date = issue_date;
        this.status = status;
        this.rollno = rollno;

    }

}
