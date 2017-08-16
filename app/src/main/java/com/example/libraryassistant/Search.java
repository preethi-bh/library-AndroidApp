package com.example.libraryassistant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.ReadableByteChannel;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity {
    Retrofit retrofit;
    RestAPI api;
    LinearLayout L;
    String username,subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        L = (LinearLayout) findViewById(R.id.l);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://library-mini1.herokuapp.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestAPI.class);
        username = getIntent().getStringExtra("Username");




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        String[] subjects=getResources().getStringArray(R.array.Subject);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    L.refreshDrawableState();
                    subject = query.toUpperCase();
                    Call<List<Books>> call = api.getBooks(username, subject);
                    call.enqueue(new Callback<List<Books>>() {
                        @Override
                        public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                            for (Books b : response.body()) {
                                CardView card = new CardView(Search.this);
                                CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT);
                                card.setLayoutParams(params);
                                card.setCardBackgroundColor(Color.parseColor("#EDE7F6"));
                                card.setUseCompatPadding(true);
                                card.setContentPadding(15, 15, 15, 15);
                                card.setCardElevation(9);

                                TextView tv = new TextView(Search.this);
                                tv.setLayoutParams(params);
                                tv.setText(b.bname + "\n" + "Bookid: " + b.bookid + "\n" + "Author: " + b.author +"\n" +"Edition: " + b.edition + "\n" + "Subject: " + b.subject);
                                tv.setTextColor(Color.BLACK);
                                tv.setTextSize(16);

                                card.addView(tv);
                                L.addView(card);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Books>> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "E" + e, Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;

    }

}





