package com.example.shoesstore.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ThongBaoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (isNetwork(context)) {
                FancyToast.makeText(context.getApplicationContext(), "Internet On !",FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false).show();
            } else {
                FancyToast.makeText(context.getApplicationContext(), "Internet Off !",FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,false).show();
            }
        }
    }


    public boolean isNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();

    }
}