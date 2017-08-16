package com.example.libraryassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ComponentName;


import android.content.Intent;



import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    EditText e1, e2, e3;
    Button b1;
    String username, rollno, password;
    RestAPI api;
    Retrofit retrofit;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText) findViewById(R.id.editText2);
        e2 = (EditText) findViewById(R.id.editText3);
        e3 = (EditText) findViewById(R.id.editText4);
        b1 = (Button) findViewById(R.id.button2);

       retrofit = new Retrofit.Builder()
                .baseUrl("https://library-mini1.herokuapp.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


       api = retrofit.create(RestAPI.class);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick (View view){
                username = e1.getText().toString().toUpperCase();
                rollno = e2.getText().toString().toUpperCase();
                password = e3.getText().toString();

                try{
                    Call<Books> call = api.login(username, rollno, password);
                    call.enqueue(new Callback<Books>() {
                        @Override
                        public void onResponse(Call<Books> call, Response<Books> response) {
                            Books b = response.body();
                            if (b.username != null) {
                                Intent i = new Intent();
                                i.setComponent(new ComponentName(Login.this, Home.class));
                                i.putExtra("Username",username);
                                i.putExtra("Rollno",rollno);
                                i.putExtra("Student",b.username);
                                startActivity(i);
                            } else {
                                new SweetAlertDialog(Login.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("Login Failed!")
                                        .setContentText(username+b.username+"Check your credentials and try again")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Books> call, Throwable t) {
                            new SweetAlertDialog(Login.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Login Failed!")
                                    .setContentText(t.getMessage())
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(null).show();

                        }
                    });
                }
                catch(Exception e){
                    Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}


