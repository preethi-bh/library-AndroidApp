package com.example.libraryassistant;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Renew extends AppCompatActivity {

    TextView formatTxt,contentTxt;
    String username,rollno;
    Retrofit retrofit;
    RestAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew);
        formatTxt=(TextView)findViewById(R.id.scan_format);
        contentTxt=(TextView)findViewById(R.id.scan_content);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://library-mini1.herokuapp.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestAPI.class);

        username=getIntent().getStringExtra("Username");
        rollno=getIntent().getStringExtra("Rollno");

    }
    public void scanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
            formatTxt.setText(scanFormat);
            contentTxt.setText(scanContent);
            try {
                Call<Books> call = api.Renew(scanContent);
                call.enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {
                        Books b = response.body();
                        Date renew=b.renew_date;

                        try{
                            Calendar cal=Calendar.getInstance();
                            cal.setTime(renew);
                            Intent i=new Intent();
                            i.setComponent(new ComponentName(getApplicationContext(),AlarmReceiver.class));

                            AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
                            PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
                            am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
                            Toast.makeText(getApplicationContext(),"Next Renew is on"+renew, Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Couldn't set alarm", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
