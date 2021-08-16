package com.example.e_voting.modl;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse extends StatusInfo {
    @SerializedName("Data")
    public UsersDetail data;


    public UsersDetail getData() {
        return data;
    }

    public void setData(UsersDetail data) {
        this.data = data;
    }
}
