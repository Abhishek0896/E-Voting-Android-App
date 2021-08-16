package com.example.e_voting.modl;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CandidateResponse extends StatusInfo {
    @SerializedName("Data")
    public ArrayList<Candidate> data;

    public ArrayList<Candidate> getData() {
        return data;
    }

    public void setData(ArrayList<Candidate> data) {
        this.data = data;
    }
}
