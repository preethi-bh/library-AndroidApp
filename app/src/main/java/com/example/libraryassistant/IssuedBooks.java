package com.example.libraryassistant;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IssuedBooks extends AppCompatActivity{
    EditText e2,e3;
    AutoCompleteTextView e1;

    LinearLayout L;
    String rollno;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         rollno=getIntent().getStringExtra("Rollno");
        db=openOrCreateDatabase(rollno, Context.MODE_PRIVATE,null);


        L=(LinearLayout)findViewById(R.id.l);

        e1 = (AutoCompleteTextView) findViewById(R.id.editText5);
        String[] books=getResources().getStringArray(R.array.books);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,books);
        e1.setAdapter(adapter);

        e2 = (EditText) findViewById(R.id.editText7);
        e3 = (EditText) findViewById(R.id.date);
        final Calendar c = Calendar.getInstance();

       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                e3.setText(sdf.format(c.getTime()));
            }

        };

        e3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IssuedBooks.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),IssuedB.class));
                i.putExtra("Rollno",rollno);
                startActivity(i);
            }
        });



    }

    public void Store(View v){
        String bookid=e1.getText().toString();
        String bname=e2.getText().toString();
        String renew_date=e3.getText().toString();


        ContentValues cv=new ContentValues();
        cv.put("Bookid",bookid);
        cv.put("Bookname",bname);
        cv.put("renewdate",renew_date);
        db.insert("Issued",null,cv);
        Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show();

    }



    }





