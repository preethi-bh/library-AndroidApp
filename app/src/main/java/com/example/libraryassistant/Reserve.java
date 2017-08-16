package com.example.libraryassistant;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Reserve extends AppCompatActivity {

    AutoCompleteTextView e2;
    Button b1;
    String username,bname,rollno;
    Retrofit retrofit;
    RestAPI api;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);


        e2 = (AutoCompleteTextView) findViewById(R.id.bname);
        String[] books = getResources().getStringArray(R.array.books);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books);
        e2.setAdapter(adapter);

        b1 = (Button) findViewById(R.id.button);
        username = getIntent().getStringExtra("Username");
        rollno = getIntent().getStringExtra("Rollno");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://library-mini1.herokuapp.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestAPI.class);

    }
        public void ReserveNow(View v){
                bname=e2.getText().toString();
                new SweetAlertDialog(Reserve.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Reserve this book?")
                        .setContentText("Make sure the Book name is correct")
                        .setConfirmText("Reserve")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                final SweetAlertDialog sd=sDialog;

                                try{
                                    Call<Books> call = api.Reserve(username,bname,rollno);
                                    call.enqueue(new Callback<Books>() {
                                        @Override
                                        public void onResponse(Call<Books> call, Response<Books> response) {
                                            try{
                                            Books b = response.body();
                                            if (b.bookid == 0) { //Bookid is set to 0 in response when books are available
                                                sd
                                                        .setTitleText("Books are Available!")
                                                        .setContentText("You can only reserve books that are not available")
                                                        .setConfirmText("OK")
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            }
                                            else {

                                                sd
                                                        .setTitleText(b.bname + " is reserved!")
                                                        .setContentText(b.bookid + " is the id of the book.Check at library to know if it is returned")
                                                        .setConfirmText("OK")
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                            }

                                                } catch (Exception e) {
                                                    sd
                                                            .setTitleText("Sorry! Couldn't reserve the book")
                                                            .setContentText("Something's wrong with your query")
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                }
                                            }

                                        @Override
                                        public void onFailure(Call<Books> call, Throwable t) {
                                            sd
                                                    .setTitleText("Oops!")
                                                    .setContentText("Couldn't complete the action")
                                                    .setConfirmText("OK")
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        }
                                    });

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "E" + e, Toast.LENGTH_LONG).show();
                                }

                            }

                        }).show();
            }



}
