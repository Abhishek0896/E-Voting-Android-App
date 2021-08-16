package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Util.ICD;
import com.example.e_voting.Util.MyWebApi;
import com.example.e_voting.Util.ShowMyDialog;
import com.example.e_voting.modl.LoginResponse;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.e_voting.Util.Constants.AADHAR_ID;
import static com.example.e_voting.Util.Constants.EMAIL;
import static com.example.e_voting.Util.Constants.ISFORVOTE;
import static com.example.e_voting.Util.Constants.MOBILE;
import static com.example.e_voting.Util.Constants.PARTYID;
import static com.example.e_voting.Util.Constants.RESULT;
import static com.example.e_voting.Util.Constants.USERNAME;
import static com.example.e_voting.Util.Constants.USERSDATA;
import static com.example.e_voting.Util.Constants.VOTER_ID;

public class MainActivity extends AppCompatActivity implements Serializable {
EditText editText;
Button btn;
TextView tvResult,tvRegister,tvLogout;
Context mContext;
ShowMyDialog showMyDialog;
ICD icd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edAadhar);
        btn = findViewById(R.id.btn);
        tvRegister = findViewById(R.id.tvRegister);
        tvLogout = findViewById(R.id.tvLogout);
        tvResult = findViewById(R.id.tvResult);
        mContext = this;
        icd = new ICD(mContext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAadhar();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Register.class);
                startActivity(intent);
            }
        });

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListCandidate.class);
                intent.putExtra(RESULT,true);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.this.finish();
                finish();
                System.exit(0);
            }
        });

    }

    private void verifyAadhar() {
        String aadhar = editText.getText().toString();
        if(aadhar.isEmpty())
        {
            editText.requestFocus();
            editText.setError("Aadhar number can not be empty");
            return;
        }
        sendLoginRequest(aadhar);
    }

    private void sendLoginRequest(String aadharID) {
        try {
            if(!icd.isInternetConnected()) {
                icd.showInternetSettingAlert();
            }else {
                MyWebApi myWebApi = MyWebApi.retrofit.create(MyWebApi.class);
                Call<LoginResponse> call = myWebApi.Login(aadharID);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            moveToNext(loginResponse);
                        } else {
                            Toast.makeText(mContext, "Tunnel not found...!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (Exception e)
        {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void moveToNext(LoginResponse loginResponse) {
        showMyDialog = new ShowMyDialog(mContext);
        if(!TextUtils.isEmpty(loginResponse.getData().getAadharID())) {

            showMyDialog.showPositivedialog("Succesfully Registered...!!!");
            Intent intent = new Intent(mContext, ListCandidate.class);
            intent.putExtra(USERNAME, loginResponse.data.getName());
            intent.putExtra(VOTER_ID, loginResponse.data.getVoterID());
            intent.putExtra(AADHAR_ID, loginResponse.data.getAadharID());
            intent.putExtra(MOBILE, loginResponse.data.getMobile());
            intent.putExtra(EMAIL, loginResponse.data.getEmail());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            showMyDialog.showNegativeDialog("Aadhar ID is not registered kindly Registered Yourself...!!!");
        }
    }
}