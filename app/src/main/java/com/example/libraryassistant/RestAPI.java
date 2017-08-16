package com.example.libraryassistant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Atreyasa on 7/6/2017.
 */

public interface RestAPI {
    @GET("rest/login")
    public Call<Books> login(@Query("username") String username, @Query("rollno") String rollno, @Query("password") String password);
    @GET("rest")
    public Call<List<Books>> getBooks(@Query("username") String username, @Query("subject") String subject);
    @PUT("rest/borrow")
    public Call<Books> Update(@Query("username") String username,@Query("status") String status,@Query("bookid") String bookid,@Query("rollno") String rollno);
    @PUT("rest/reserve")
    public Call<Books> Reserve(@Query("username") String username,@Query("bname") String bname,@Query("rollno") String rollno);
    @PUT("rest/renew")
    public Call<Books> Renew(@Query("barcode") String barcode);

}
