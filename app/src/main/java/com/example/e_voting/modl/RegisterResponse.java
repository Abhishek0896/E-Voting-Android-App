package com.example.e_voting.modl;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends StatusInfo {
    @SerializedName("Data")
    private String Data;


    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
