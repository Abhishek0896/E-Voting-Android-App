package com.example.e_voting.modl;

import com.google.gson.annotations.SerializedName;

public class StatusInfo {
    @SerializedName("Message")
    private String message;
    @SerializedName("statusCode")
    private String statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
