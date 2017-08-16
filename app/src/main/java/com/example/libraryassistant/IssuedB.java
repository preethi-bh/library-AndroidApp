package com.example.libraryassistant;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IssuedB extends AppCompatActivity {
    SQLiteDatabase db;
    LinearLayout L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String rollno=getIntent().getStringExtra("Rollno");
        L=(LinearLayout)findViewById(R.id.list);
        setContentView(R.layout.activity_issued_b);
        try {
            db = openOrCreateDatabase(rollno, MODE_PRIVATE, null);
            Cursor b = db.rawQuery("Select * from Issued",null);

                while (b.moveToNext()) {
                    CardView card = new CardView(IssuedB.this);
                    CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT);
                    card.setLayoutParams(params);
                    card.setCardBackgroundColor(Color.parseColor("#EDE7F6"));
                    card.setUseCompatPadding(true);
                    card.setContentPadding(15, 15, 15, 15);
                    card.setCardElevation(9);

                    TextView tv = new TextView(IssuedB.this);
                    tv.setLayoutParams(params);
                    tv.setText("Bookid: " + b.getString(0) + "\n" + "Book Name" + b.getString(1) + "\n" + "Renew_Date" + b.getString(2));
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(16);

                    card.addView(tv);
                    L.addView(card);
                b.close();
            }
        }
        catch(Exception e){
            Toast.makeText(IssuedB.this,"E: "+e,Toast.LENGTH_LONG).show();
        }
    }
}
