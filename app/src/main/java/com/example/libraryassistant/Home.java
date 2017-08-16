package com.example.libraryassistant;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    TextView t;
    SQLiteDatabase db;
    LinearLayout L;
    String username,rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        L=(LinearLayout)findViewById(R.id.l);

        t=(TextView)findViewById(R.id.name);


        username=getIntent().getStringExtra("Username");
        rollno=getIntent().getStringExtra("Rollno");
        String student=getIntent().getStringExtra("Student");

        t.setText("Hello " +student+"!");

         db = openOrCreateDatabase(rollno, MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Borrowed(Bookid text,BookName text,Author text,Edition text,Subject text);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Issued(Bookid text,Bookname text,renewdate text);");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),MyBooks.class));
                i.putExtra("Username",username);
                i.putExtra("Rollno",rollno);
                startActivity(i);
            }
        });
    }
    public void borrow(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),Borrow.class));
        i.putExtra("Username",username);
        i.putExtra("Rollno",rollno);
        startActivity(i);
    }
    public void search(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),Search.class));
        i.putExtra("Username",username);
        startActivity(i);
    }
    public void renew(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),Renew.class));
        i.putExtra("Username",username);
        i.putExtra("Rollno",rollno);
        startActivity(i);
    }
    public void reserve(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(),Reserve.class));
        i.putExtra("Username",username);
        i.putExtra("Rollno",rollno);
        startActivity(i);
    }

}
