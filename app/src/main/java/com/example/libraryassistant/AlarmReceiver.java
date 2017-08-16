package com.example.libraryassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

/**
 * Created by Atreyasa on 7/17/2017.
 */

@SuppressWarnings("deprecation")
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator v=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }
}


