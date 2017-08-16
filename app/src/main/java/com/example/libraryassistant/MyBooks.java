package com.example.libraryassistant;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyBooks extends AppCompatActivity {
        String rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        rollno=getIntent().getStringExtra("Rollno");
    }
    public void issued(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),IssuedBooks.class));
        i.putExtra("Rollno",rollno);
        startActivity(i);
    }
    public void borrowed(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),OwnedBooks.class));
        i.putExtra("Rollno",rollno);
        startActivity(i);
    }
}
