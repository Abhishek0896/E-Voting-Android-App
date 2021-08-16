package com.example.e_voting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Util.FingerPrintHandler;
import com.example.e_voting.Util.ICD;
import com.example.e_voting.Util.MyPref;
import com.example.e_voting.Util.MyWebApi;
import com.example.e_voting.Util.ShowMyDialog;
import com.example.e_voting.modl.LoginResponse;
import com.example.e_voting.modl.RegisterResponse;
import com.example.e_voting.modl.UsersDetail;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.e_voting.Util.Constants.AADHAR_ID;
import static com.example.e_voting.Util.Constants.EMAIL;
import static com.example.e_voting.Util.Constants.ISFORVOTE;
import static com.example.e_voting.Util.Constants.MOBILE;
import static com.example.e_voting.Util.Constants.PARTYID;
import static com.example.e_voting.Util.Constants.STRING_KEY_NAME;
import static com.example.e_voting.Util.Constants.USERNAME;
import static com.example.e_voting.Util.Constants.USERSDATA;
import static com.example.e_voting.Util.Constants.VOTER_ID;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Register extends AppCompatActivity  implements Serializable {
    EditText name, phone, email, address, aadhar, voterID;
    Button submit;
    String UserName, mobile, emailAddress, Address, VoterIDNumber, aadharID;
    boolean isForVote = false;
    UsersDetail usersDetail;
    Context mContext;
    RegisterResponse registerResponse;
    ShowMyDialog showMyDialog;
    ProgressBar pg;
    ScrollView form;
    ConstraintLayout biometric;
    TextView tvLabel,tvLabel2;
    ImageView tvfingerPrint;
    FingerprintManager fingerprintmanager;
    KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    MyPref myPref;
    int partyID = -1;
    LoginResponse loginResponse;
    String uname, uemail,umobile,uvoterID,uaadharID;
    ICD icd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;

        initComponet();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isForVote = extras.getBoolean(ISFORVOTE);
            partyID = extras.getInt(PARTYID);
            uname = extras.getString(USERNAME);
            umobile = extras.getString(MOBILE);
            uemail = extras.getString(EMAIL);
            uvoterID = extras.getString(VOTER_ID);
            uaadharID = extras.getString(AADHAR_ID);
            getData();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

    }

    private void initComponet()
    {

        name = findViewById(R.id.tvRegisterName);
        phone = findViewById(R.id.tvRegisterMobile);
        email = findViewById(R.id.tvRegisterEmail);
        address = findViewById(R.id.tvRegisterAddress);
        aadhar = findViewById(R.id.tvRegisterAadhar);
        voterID = findViewById(R.id.tvRegisterVoterID);
        submit = findViewById(R.id.Submit);
        pg = findViewById(R.id.pbRegister);
        form = findViewById(R.id.form);
        biometric = findViewById(R.id.biometric);
        tvLabel = findViewById(R.id.tvLabel);
        tvLabel2 = findViewById(R.id.tvLabelBottom);
        tvfingerPrint = findViewById(R.id.tvfingerPrint);

        showMyDialog = new ShowMyDialog(mContext);
        myPref = new MyPref(mContext);


    }

    private void verify() {
        UserName = name.getText().toString().toLowerCase();
        mobile = phone.getText().toString().toLowerCase();
        emailAddress = email.getText().toString().toLowerCase();
        Address = address.getText().toString().toLowerCase();
        VoterIDNumber = voterID.getText().toString().toLowerCase();
        aadharID = aadhar.getText().toString().toLowerCase();

        if (UserName.isEmpty()) {
            name.requestFocus();
            name.setError("Name can't be Empty");
            return;
        }
        if (mobile.isEmpty()) {
            phone.requestFocus();
            phone.setError("Phone can't be Empty");
            return;
        }
        if (emailAddress.isEmpty()) {
            email.requestFocus();
            email.setError("Email can't be Empty");
            return;
        }
        if (Address.isEmpty() &&  !isForVote) {
            address.requestFocus();
            address.setError("address can't be Empty");
            return;
        }
        if (VoterIDNumber.isEmpty()) {
            voterID.requestFocus();
            voterID.setError("voterID can't be Empty");
            return;
        }
        if (aadharID.isEmpty()) {
            aadhar.requestFocus();
            aadhar.setError("Aadhar Number can't be Empty");
            return;
        }
        verifyEmail();
    }

    private void verifyEmail() {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.matches()) {
            email.requestFocus();
            email.setError("Please Enter Correct Email Address");
            return;
        }
        fingerPrints();
    }

    private void saveData() {
        myPref.putString(USERNAME, usersDetail.name);
        myPref.putString(VOTER_ID, usersDetail.VoterID);
        myPref.putString(AADHAR_ID, usersDetail.AadharID);
        myPref.putString(MOBILE, usersDetail.mobile);
        myPref.putString(EMAIL,usersDetail.Email);
    }

    private void getData()
    {
        name.setText(uname);
        phone.setText(umobile);
        email.setText(uemail);
        aadhar.setText(uaadharID);
        voterID.setText(uvoterID);
        address.setVisibility(View.GONE);
//        verify();
    }

    private void submitData() {
        if(!icd.isInternetConnected()) {
            icd.showInternetSettingAlert();
        }else {
            pg.setVisibility(View.VISIBLE);
            if (isForVote) {
                Vote();
            } else {
                RegisterUser();
            }
        }
    }

    private void RegisterUser() {
        try {
            MyWebApi myWebApi = MyWebApi.retrofit.create(MyWebApi.class);
            Call<RegisterResponse> call = myWebApi.RegisterUser(usersDetail);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        registerResponse = response.body();
                        showDialog();
                    } else {
                        Toast.makeText(mContext, "Tunnel is not found...!!!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void Vote() {
        try {
            MyWebApi myWebApi = MyWebApi.retrofit.create(MyWebApi.class);
            Call<RegisterResponse> call = myWebApi.Vote(usersDetail);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        registerResponse = response.body();
                        showDialog();
                    } else {
                        Toast.makeText(mContext, "Tunnel is not found...!!!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showDialog() {
        pg.setVisibility(View.GONE);
        if (registerResponse.getData().equals("You have succesfully voted...!!!") || registerResponse.getData().equals("You have succesfully registered...!!!"))
            showMyDialog.showPositivedialogRegistration(registerResponse.getData());
        else
            showMyDialog.showNegativeDialogRegistration(registerResponse.getData());
//        Intent intent = new Intent(mContext,MainActivity.class);
//        startActivity(intent);
    }

    private void fingerPrints() {
        usersDetail = new UsersDetail(UserName, aadharID, VoterIDNumber, mobile, Address, emailAddress, partyID);
        form.setVisibility(View.GONE);
        biometric.setVisibility(View.VISIBLE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintmanager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintmanager.isHardwareDetected()) {
                tvLabel2.setText("No hardware detected");
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                tvLabel2.setText("permission not granted to use finger print");
            } else if (!keyguardManager.isKeyguardSecure()) {
                tvLabel2.setText("secure your phone with atleast one type of add lock");
            } else if (!fingerprintmanager.hasEnrolledFingerprints()) {
                tvLabel2.setText("Add atleast one fingerprint");
            } else {
                tvLabel2.setText("Place your finger here");

                generateKey();

                if(cipherInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerPrintHandler fingerPrintHandler = new FingerPrintHandler(this,usersDetail,isForVote);
                    fingerPrintHandler.startAuth(fingerprintmanager, cryptoObject);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(STRING_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException | java.security.cert.CertificateException e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(STRING_KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (CertificateException |KeyStoreException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public void CallSubmitData(UsersDetail detail, boolean isForVote, Context getContext)
    {
        mContext = getContext;
        icd = new ICD(mContext);
         form = (ScrollView) ((Activity) mContext).findViewById(R.id.form);
         biometric = (ConstraintLayout)((Activity)mContext).findViewById(R.id.biometric);
         pg = (ProgressBar)((Activity)mContext).findViewById(R.id.pbRegister);
//        initComponet();
        showMyDialog = new ShowMyDialog(mContext);
        myPref = new MyPref(mContext);
        this.usersDetail = detail;
        this.isForVote = isForVote;
        form.setVisibility(View.VISIBLE);
        biometric.setVisibility(View.GONE);
        submitData();
    }

}