package com.example.e_voting.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Button;

public class ICD {
    private Context mContext;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public ICD(Context mContext){
        this.mContext = mContext;
    }

    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showInternetSettingAlert(){
        builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Internet is Not Connected, Please Connect to Internet.");
        builder.setPositiveButton(("Ok"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton(("Cancel"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.GREEN);
    }

    public void dismissDialog(){
        if (alertDialog!= null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}

