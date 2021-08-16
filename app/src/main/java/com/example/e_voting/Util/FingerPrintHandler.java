package com.example.e_voting.Util;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.e_voting.R;
import com.example.e_voting.Register;
import com.example.e_voting.modl.UsersDetail;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    UsersDetail usersDetail;
    boolean isForVote;
    Register register = new Register();
    public FingerPrintHandler(Context context, UsersDetail detail, boolean isForVote) {
        this.context = context;
        this.isForVote=isForVote;
        this.usersDetail = detail;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal , 0 , this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an auth error"+ errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error : "+helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app", true);
    }

    private void update(String status, boolean b) {
        try{
            TextView tv1 = (TextView) ((Activity) context).findViewById(R.id.tvLabel);
            ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.tvfingerPrint);
            tv1.setText(status);
            if (b == false) {
                tv1.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_error));
            } else {
                tv1.setText("Biometric access succesfull...!!!");
                tv1.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_on_primary));
                imageView.setImageResource(R.mipmap.done);
                register.CallSubmitData(usersDetail, isForVote,context);
            }
        }catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
