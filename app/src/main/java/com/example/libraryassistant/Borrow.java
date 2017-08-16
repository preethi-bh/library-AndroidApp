package com.example.libraryassistant;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Borrow extends AppCompatActivity {

    EditText e1;
    Button b1;
    String username,rollno,bookid;
    Retrofit retrofit;
    RestAPI api;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);


        e1 = (EditText) findViewById(R.id.bookid);
        b1 = (Button) findViewById(R.id.button7);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://library-mini1.herokuapp.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestAPI.class);
        username = getIntent().getStringExtra("Username");
        rollno = getIntent().getStringExtra("Rollno");


    }
    public void borrow1(View v){
                bookid = e1.getText().toString();

                    new SweetAlertDialog(Borrow.this, SweetAlertDialog.WARNING_TYPE)

                            .setTitleText("Borrow this book?")
                            .setContentText("Make sure the BookId is correct")
                            .setConfirmText("Borrow")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    final SweetAlertDialog sd = sDialog;
                                    try {
                                        Call<Books> call = api.Update(username,"Borrowed", bookid, rollno);
                                        call.enqueue(new Callback<Books>() {
                                            @Override
                                            public void onResponse(Call<Books> call, Response<Books> response) {
                                                Books b = response.body();
                                                try {
                                                    sd
                                                            .setTitleText("Borrowed!")
                                                            .setContentText(b.bname + " is borrowed.Collect it before the end of the day")
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                    SQLiteDatabase db = openOrCreateDatabase(rollno, MODE_PRIVATE, null);
                                                        ContentValues cv = new ContentValues();
                                                        cv.put("Bookid", b.bookid);
                                                        cv.put("Bookname", b.bname);
                                                        cv.put("Author", b.author);
                                                        cv.put("Edition", b.edition);
                                                        cv.put("Subject", b.subject);
                                                        db.insert("Borrowed", null, cv);



                                                } catch (Exception e) {
                                                    sd
                                                            .setTitleText("Sorry! Something is wrong!")
                                                            .setContentText(e + " You are not allowed to borrow this book")
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Books> call, Throwable t) {
                                                sd
                                                        .setTitleText("Oops!")
                                                        .setContentText("Sorry couldn't complete the action")
                                                        .setConfirmText("OK")
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            }
                                        });

                                    } catch (Exception e) {
                                        Toast.makeText(Borrow.this, "E" + e, Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .show();



                }






    }

