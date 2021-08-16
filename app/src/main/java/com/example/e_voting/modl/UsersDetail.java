package com.example.e_voting.modl;

import com.google.gson.annotations.SerializedName;

public class UsersDetail extends StatusInfo {


    @SerializedName("Name")
    public String name;
    @SerializedName("AadharID")
    public String AadharID;
    @SerializedName("VoterID")
    public String VoterID;
    @SerializedName("Mobile")
    public String mobile;
    @SerializedName("Address")
    public String Address;
    @SerializedName("Email")
    public String Email;
    @SerializedName("CandidateID")
    public int CandidateID;

    public UsersDetail(String name, String aadharID, String voterID, String mobile, String address, String email, int candidateID) {
        super();
        this.name = name;
        AadharID = aadharID;
        VoterID = voterID;
        this.mobile = mobile;
        Address = address;
        Email = email;
        CandidateID = candidateID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadharID() {
        return AadharID;
    }

    public void setAadharID(String aadharID) {
        AadharID = aadharID;
    }

    public String getVoterID() {
        return VoterID;
    }

    public void setVoterID(String voterID) {
        VoterID = voterID;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(int candidateID) {
        CandidateID = candidateID;
    }
}
